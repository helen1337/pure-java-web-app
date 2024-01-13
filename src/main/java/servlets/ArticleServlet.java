package servlets;

import models.Article;
import service.ArticleService;
import utils.ArticleParser;
import utils.SessionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Servlet handling requests related to individual articles
 */
public class ArticleServlet extends HttpServlet {
    ArticleService articleService = ArticleService.getInstance();

    /**
     * Handles GET requests related to articles, such as editing, deleting,
     * adding, or viewing an article.
     *
     *
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for sending the response to the client
     * @throws ServletException if a servlet-specific problem occurs
     * @throws IOException      if an I/O error occurs while handling the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            if (saveURL(request, action)) {
                switch (action) {
                    case "edit" -> editArticle(request, response);
                    case "delete" -> deleteArticle(request, response);
                    case "add" -> {
                        String nextJSP = "/newArticle.jsp";
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                        dispatcher.forward(request, response);
                    }
                }
            }
            else {
                SessionManager.sendMessageToSession(request,
                        "You don't have permission to delete or edit this article.");
                response.sendRedirect("/my-blog/all_articles");
            }
        }
        else {
            String id = request.getParameter("id");
            Article article = articleService.getArticle(id);
            request.setAttribute("article", article);
            String nextJSP = "/article.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request, response);
        }
    }

    /**
     * Checks if the current user has permission for the specified action.
     * Ensures data security when manually entering URL
     *
     * @param request The HttpServletRequest object
     * @param action  The action to be performed
     * @return true if user's authenticated and is the article's author or action == "add"
     * otherwise false
     */
    // If user isn't authenticated or isn't the article's author return false;
    // saveURL() ensures data security when manually entering URL.
    private boolean saveURL(HttpServletRequest request, String action) {
        if (action.equals("add")) {
            return true;
        }
        if (!SessionManager.isUserInSession(request)) {
            return false;
        }
        String checkAuthor = request.getParameter("checkAuthor");
        String authorArticle = request.getParameter("author");
        return Objects.equals(authorArticle, checkAuthor);
    }

    /**
     * Forwards the request to edit an article
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void editArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Article article = articleService.getArticle(id);
        request.setAttribute("article", article);
        request.setAttribute("action", "edit");
        String nextJSP = "/newArticle.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    /**
     * Handles the deletion of an article
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException If an input or output exception occurs
     */
    private void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String message;
        boolean result = articleService.deleteArticle(id);
        if (result) {
            SessionManager.sendMessageToSession(request,
                    "The article has been successfully deleted!");
        }
        else {
            SessionManager.sendMessageToSession(request,
                    "The article hasn't been deleted! Please, try again later.");
        }
        response.sendRedirect("/my-blog/all_articles");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        boolean result;
        Article article;
        switch (action) {
            case "add":
                article = ArticleParser.parsArticle(request);
                result = articleService.addArticle(article);
                if (result) {
                    SessionManager.sendMessageToSession(request,
                            "The article has been successfully created!");
                }
                else {
                    SessionManager.sendMessageToSession(request,
                            "Hm, something's wrong... Please, try again later!");
                }
                response.sendRedirect("/my-blog/all_articles");
                break;
            case "edit":
                article = ArticleParser.parsArticle(request);
                result = articleService.editArticle(article);
                if (result) {
                    SessionManager.sendMessageToSession(request,
                            "The article has been successfully edited!");
                }
                else {
                    SessionManager.sendMessageToSession(request,
                            "Hm, something's wrong... Please, try again later!");
                }
                response.sendRedirect("/my-blog/all_articles");
                break;
        }
    }
}
