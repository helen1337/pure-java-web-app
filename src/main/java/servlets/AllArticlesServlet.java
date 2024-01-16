package servlets;

import models.Article;
import service.ArticleService;
import utils.SessionManager;
import utils.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Servlet handling requests for displaying and searching articles
 */
public class AllArticlesServlet extends HttpServlet {

    ArticleService articleService;
    String nextJSP = "/allArticles.jsp";

    /**
     * Handles HTTP GET requests. Retrieves theme information
     * and forwards the request to the appropriate view.
     *
     * <ul>
     *     This method initializes the ArticleService,
     *     retrieves a list of available themes, sets the themeList attribute
     *     in the request scope, and determines the action specified
     *     in the request to perform the corresponding operation:
     *
     *      <ul>
     *     <li> If the action is "searchAuthorArticles",
     *          the method forwards the request to the view displaying articles
     *          authored by a specific author.</li>
     *
     *     <li> Otherwise it retrieves the "theme" parameter,
     *          forwards the request to the method handling
     *          the list of articles for the specified theme.</li>
     *      </ul>
     * </ul>
     *
     * <p>This method uses a try-catch block to handle potential exceptions
     * that may occur while working with the database by calling a method
     * that generates an error message and redirects the user to the main page
     * with an alert message.</p>
     *
     * @see #forwardMainPageWithError(HttpServletRequest, HttpServletResponse, Exception)
     * @see #forwardAuthorArticles(HttpServletRequest, HttpServletResponse) 
     * @see #forwardListAllArticles(HttpServletRequest, HttpServletResponse) 
     * @see #forwardListArticles(HttpServletRequest, HttpServletResponse, String) 
     * @see #forwardListArticlesByTheme(HttpServletRequest, HttpServletResponse, String)
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException      If an input or output exception occurs
     * @throws ServletException If a servlet-specific problem occurs
     * */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            articleService = ArticleService.getInstance();
            List<String> themeList = articleService.getAllTheme();
            String action = request.getParameter("action");
            request.setAttribute("themeList", themeList);
            if (Objects.equals(action, "searchAuthorArticles")) {
                forwardAuthorArticles(request, response);
            } else {
                String theme = request.getParameter("theme");
                forwardListArticles(request, response, theme);
            }
        } catch (SQLException | ClassNotFoundException e) {
            forwardMainPageWithError(request, response, e);
        }
    }

    /**
     * Forwards the request to display the list of articles based on the specified theme.
     *
     * @see #forwardListArticles(HttpServletRequest, HttpServletResponse, String)
     * @see #forwardListArticlesByTheme(HttpServletRequest, HttpServletResponse, String) 
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param theme    The theme by which to filter articles
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void forwardListArticles(HttpServletRequest request, HttpServletResponse response, String theme)
            throws ServletException, IOException {
        if (StringUtils.isEmpty(theme)) {
            forwardListAllArticles(request, response);
        }
        else {
            forwardListArticlesByTheme(request, response, theme);
        }
    }

    /**
     * Forwards the request to display the list of all articles.
     *
     * <p>This method uses the ArticleService to retrieve a list of all articles,
     * sets the articlesList attribute in the request scope,
     * and forwards the request to the JSP page
     * specified by the nextJSP parameter.</p>
     *
     * <p>This method uses a try-catch block to handle potential exceptions
     * that may occur while working with the database by calling a method
     * that generates an error message and redirects the user to the main page
     * with an alert message.</p>
     *
     * @see #nextJSP
     * @see #forwardMainPageWithError(HttpServletRequest, HttpServletResponse, Exception) 
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void forwardListAllArticles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            articleService = ArticleService.getInstance();
            List<Article> articlesList = articleService.getAllArticle();
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            request.setAttribute("articlesList", articlesList);
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            forwardMainPageWithError(request, response, e);
        }
    }

    /**
     * Forwards the request to display the list of articles based on the specified theme.
     *
     * <p>This method uses the ArticleService to retrieve a list of articles 
     * based on the provided theme, sets the articlesList attribute in the request scope,
     * and forwards the request to the JSP page specified by the nextJSP parameter.</p>
     *
     * <p>This method uses a try-catch block to handle potential exceptions
     * that may occur while working with the database by calling a method
     * that generates an error message and redirects the user to the main page
     * with an alert message.</p>
     * 
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param theme    The theme by which to filter articles
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void forwardListArticlesByTheme(HttpServletRequest request, HttpServletResponse response, String theme)
            throws ServletException, IOException {
        try {
            articleService = ArticleService.getInstance();
            List<Article> articlesList = articleService.getArticle("theme", theme);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            request.setAttribute("articlesList", articlesList);
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            forwardMainPageWithError(request, response, e);
        }
    }

    /**
     * Forwards the request to display the list of articles based on the specified author.
     *
     * <p>This method retrieves the author parameter from the HttpServletRequest,
     * uses the ArticleService to retrieve a list of articles based on the provided author,
     * sets the articlesList attribute in the request scope, and forwards the request to the JSP
     * page specified by the nextJSP parameter.</p>
     *
     * <p>This method uses a try-catch block to handle potential exceptions
     * that may occur while working with the database by calling a method
     * that generates an error message and redirects the user to the main page
     * with an alert message.</p>
     *
     * @see #nextJSP
     * @see #forwardMainPageWithError(HttpServletRequest, HttpServletResponse, Exception)
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void forwardAuthorArticles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String author = request.getParameter("author");
        try {
            articleService = ArticleService.getInstance();
            List<Article> articlesList = articleService.getArticle("author", author);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            request.setAttribute("articlesList", articlesList);
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            forwardMainPageWithError(request, response, e);
        }
    }

    /**
     * Forwards the request to the main page (/my-blog) with an error message
     * stored in the session.
     *
     * <p>This method is used when an exception occurs and need to notify the user
     * about the error by storing the error message in the session and
     * redirecting them to the main page.</p>
     *
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param e The exception that occurred, providing additional information about the error
     * @throws IOException If an input or output exception occurs
     *
     * @see SessionManager#sendMessageToSession(HttpServletRequest, String)
     */
    private void forwardMainPageWithError(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        SessionManager.sendMessageToSession(request, e.getMessage());
        response.sendRedirect("/my-blog");
    }

    /**
     * Handles GET requests by delegating to the corresponding doPost method.
     *
     * This method is used to ensure that both GET and POST requests are handled consistently.
     *
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException If an input or output exception occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}