package servlets;

import models.Article;
import service.ArticleService;
import utils.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AllArticlesServlet extends HttpServlet {

    ArticleService articleService = ArticleService.getInstance();
    String nextJSP = "/allArticles.jsp";
    List<String> themeList = articleService.getAllTheme();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        request.setAttribute("themeList", themeList);
        if (Objects.equals(action, "searchAuthorArticles")) forwardAuthorArticles(request, response);
        else {
            String theme = request.getParameter("theme");
            forwardListArticles(request, response, theme);
        }
    }


    private void forwardListArticles(HttpServletRequest request, HttpServletResponse response, String theme)
            throws ServletException, IOException {
        if (StringUtils.isEmpty(theme)) forwardListAllArticles(request, response);
        else forwardListArticlesByTheme(request, response, theme);
    }
    private void forwardListAllArticles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Article> articlesList = articleService.getAllArticle();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        request.setAttribute("articlesList", articlesList);
        dispatcher.forward(request, response);
    }

    private void forwardListArticlesByTheme(HttpServletRequest request, HttpServletResponse response, String theme)
            throws ServletException, IOException {
        List<Article> articlesList = articleService.getArticle("theme", theme);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        request.setAttribute("articlesList", articlesList);
        dispatcher.forward(request, response);

    }

    private void forwardAuthorArticles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String author = request.getParameter("author");
        List<Article> articlesList = articleService.getArticle("author", author);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        request.setAttribute("articlesList", articlesList);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
