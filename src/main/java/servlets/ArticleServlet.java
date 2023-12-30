package servlets;


import models.Article;
import service.ArticleService;
import utils.EditArticleUtils;
import utils.NewArticleUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;


public class ArticleServlet extends HttpServlet {

    ArticleService articleService = ArticleService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            if (saveURL(request, action)) {
                switch (action) {
                    case "edit":
                        editArticle(request, response);
                        break;
                    case "delete":
                        deleteArticle(request, response);
                        break;
                    case "add":
                        String nextJSP = "/newArticle.jsp";
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                        dispatcher.forward(request, response);
                }
            } else {
                HttpSession session = request.getSession();
                String message = "You don't have permission to delete or edit this article.";
                session.setAttribute("message", message);
                response.sendRedirect("/my-blog/all_articles");
            }
        } else {
            String id = request.getParameter("id");
            Article article = articleService.getArticle(id);
            request.setAttribute("article", article);
            String nextJSP = "/article.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request, response);
        }
    }

    // If user isn't authenticated or isn't the article's author return false;
    // saveURL() ensures data security when manually entering URL.
    private boolean saveURL(HttpServletRequest request, String action) {
        if (action.equals("add")) return true;
        HttpSession session = request.getSession();
        if (null == session.getAttribute("user")) return false;
        String checkAuthor = request.getParameter("checkAuthor");
        String authorArticle = request.getParameter("author");
        return Objects.equals(authorArticle, checkAuthor);
    }

    private void editArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Article article = articleService.getArticle(id);
        request.setAttribute("article", article);
        request.setAttribute("action", "edit");
        String nextJSP = "/newArticle.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        String message;
        boolean result = articleService.deleteArticle(id);
        if (result) message = "The article has been successfully deleted!";
        else message = "The article hasn't been deleted! Please, try again later.";
        session.setAttribute("message", message);
        response.sendRedirect("/my-blog/all_articles");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        boolean result;
        String message;
        switch (action) {
            case "add" -> {
                result = NewArticleUtils.createArticle(request);
                if (result) message = "The article has been successfully created!";
                else message = "Hm, something's wrong... Please, try again later!";
                session.setAttribute("message", message);
                response.sendRedirect("/my-blog/all_articles");
            }
            case "edit" -> {
                result = EditArticleUtils.editArticle(request);
                if (result) message = "The article has been successfully edited!";
                else message = "Hm, something's wrong... Please, try again later!";
                session.setAttribute("message", message);
                response.sendRedirect("/my-blog/all_articles");
            }
        }
    }

}
