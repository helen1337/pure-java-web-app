package servlets;

import models.Article;
import models.User;
import service.ArticleService;
import utils.ArticleParser;
import utils.SessionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Servlet handling requests related to individual articles
 */
public class ArticleServlet extends HttpServlet {
    ArticleService articleService;

    /**
     * Handles GET requests related to articles, such as editing, deleting,
     * adding, or viewing an article.
     *
     * <p>  Checks the "id" and "action" parameters in the request. </p>
     * <ul>
     *     If "id" isn't null:
     *     <ul>
     *         <p> Uses ArticleService instance to retrieve the Article
     *         instance based on the "id". </p>
     *         <p> If "action" is null: calls method
     *         forwardArticle(HttpServletRequest, HttpServletResponse, Article)
     *         to forward the request to the view displaying the article. </p>
     *         <p> If "action" isn't null: </p>
     *         <p> Checks the user in session, if user exists and user is article's author
     *         continue following action otherwise redirect to the all articles page
     *         with the permission warning:</p>
     *         <ul>
     *             <p> "action" is 'edit': calls editArticle(HttpServletRequest,
     *             HttpServletResponse, Article) to forward request to the view editing the article.
     *             </p>
     *
     *             <p> "action" is 'delete' calls deleteArticle(HttpServletRequest,
     *             HttpServletResponse, Article) to forward request to the view deleting the article.
     *             </p>
     *
     *             <p> otherwise calls forwardMainPageWithError (HttpServletRequest, HttpServletResponse, Exception)
     *             to forward request to the main page with an error message about the wrong URL.
     *             </p>
     *         </ul>
     *     </ul>
     *     This code in "id!=null" block uses a try-catch block to handle potential exceptions
     *     that may occur while working with the database by calling a method
     *     that generates an error message and redirects the user to the main page
     *     with an alert message.
     *     <p></p>
     *     If "id" is null:
     *     <ul>
     *         and "action" is 'add': calls forwardCreateArticlePage(HttpServletRequest,
     *         HttpServletResponse, Article) to forward request to the view create the article.
     *     </ul>
     *
     *     Otherwise calls forwardMainPageWithError (HttpServletRequest, HttpServletResponse, Exception)
     *     to forward request to the main page with an error message about the wrong URL.
     * </ul>
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for sending the response to the client
     * @throws ServletException if a servlet-specific problem occurs
     * @throws IOException      if an I/O error occurs while handling the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (id != null) {
            try {
                articleService = ArticleService.getInstance();
                Article article = articleService.getArticle(id);
                if (action == null) {
                    forwardArticle(request, response, article);
                } else {
                    if (saveURL(request, response, article)) {
                        switch (action) {
                            case "edit" -> editArticle(request, response, article);
                            case "delete" -> deleteArticle(request, response, article);
                            default -> throw new RuntimeException("This URL does not exist");
                        }
                    } else {
                        SessionManager.sendMessageToSession(request,
                                "You don't have permission to delete or edit this article.");
                        response.sendRedirect("/my-blog/all_articles");
                    }
                }
            } catch (SQLException | ClassNotFoundException | RuntimeException e) {
                forwardMainPageWithError(request, response, e);
            }
        }
        else if (action.equals("add")) {
            forwardCreateArticlePage(request, response);
        }
        else {
            forwardMainPageWithError(request, response, new RuntimeException("This URL does not exist"));
        }
    }

    /**
     * Forwards the given {@link Article} to the "/article.jsp" page.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for sending the response to the client
     * @param article  The Article to be forwarded.
     * @throws ServletException If a servlet-specific problem occurs.
     * @throws IOException      If an I/O error occurs.
     */
    private void forwardArticle(HttpServletRequest request, HttpServletResponse response, Article article) throws ServletException, IOException {
        request.setAttribute("article", article);
        String nextJSP = "/article.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    /**
     * Forwards the request to the "/newArticle.jsp" page to create a new article.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for sending the response to the client
     * @throws ServletException If a servlet-specific problem occurs.
     * @throws IOException      If an I/O error occurs.
     */
    private void forwardCreateArticlePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextJSP = "/newArticle.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    /**
     * Checks if the current user has permission for the specified action.
     * Ensures data security when manually entering URL.
     *
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param article The Article object to check
     * @return true if user's authenticated and is the article's author or action == "add"
     * otherwise false
     */
    private boolean saveURL(HttpServletRequest request, HttpServletResponse response, Article article) throws IOException {
        if (!SessionManager.isUserInSession(request)) {
            return false;
        }
        User user = SessionManager.getUserFromSession(request);
        return Objects.equals(user.getUser_id(), article.getUser_id());
    }

    /**
     * Handles the request to edit an existing article.
     *
     * <p>This method retrieves the article with the specified ID,
     * sets it as an attribute in the request scope, and forwards
     * the request to the newArticle.jsp page for editing.
     * It also sets the "action" attribute to "edit" to indicate
     * that the page is being used for editing an existing article.</p>
     *
     * <p>This method uses a try-catch block to handle potential exceptions
     * that may occur while working with the database by calling a method
     * that generates an error message and redirects the user to the main page
     * with an alert message.</p>
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void editArticle(HttpServletRequest request, HttpServletResponse response, Article article) throws ServletException, IOException {
        request.setAttribute("article", article);
        request.setAttribute("action", "edit");
        String nextJSP = "/newArticle.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    /**
     * Handles the request to delete an existing article.
     *
     * <p>This method retrieves the article with the specified ID, 
     * attempts to delete it using the ArticleService instance, and sends a message 
     * to the user's session based on the result of the deletion. It then redirects the user
     * to the main articles page (/my-blog/all_articles).</p>
     *
     * <p>This method uses a try-catch block to handle potential exceptions
     * that may occur while working with the database by calling a method
     * that generates an error message and redirects the user to the main page
     * with an alert message.</p>
     * 
     * @see #forwardMainPageWithError(HttpServletRequest, HttpServletResponse, Exception) 
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException If an input or output exception occurs
     */
    private void deleteArticle(HttpServletRequest request, HttpServletResponse response, Article article) throws IOException {
        boolean result = articleService.deleteArticle(article.getId());
        if (result) {
            SessionManager.sendMessageToSession(request,
                    "The article has been successfully deleted!");
        } else {
            SessionManager.sendMessageToSession(request,
                    "The article hasn't been deleted! Please, try again later.");
        }
        response.sendRedirect("/my-blog/all_articles");
    }

    /**
     * Handles HTTP POST requests for adding or editing articles.
     *
     * <p>This method parser article information from the request,
     * checks the "action" parameter in the request to determine
     * the appropriate action and invokes the corresponding method
     * for the articleService instance.</p>
     *
     * <p>Depending on the return value of those methods, the method of sending a message
     * to the session is called and then forwards to the page with all articles
     * (/my-blog/all_articles).</p>
     *
     * <p>This method uses a try-catch block to handle potential exceptions
     * that may occur while working with the database by calling a method
     * that generates an error message and redirects the user to the main page
     * with an alert message.</p>
     *
     * @see #forwardMainPageWithError(HttpServletRequest, HttpServletResponse, Exception)
     * @param request  The HttpServletRequest object containing the client's POST request.
     * @param response The HttpServletResponse object for sending responses to the client.
     * @throws IOException      If an input or output exception occurs during the servlet processing.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        boolean result;
        Article article;
        try {
            articleService = ArticleService.getInstance();
            article = ArticleParser.parsArticle(request);
            switch (action) {
                case "add":
                    result = articleService.addArticle(article);
                    if (result) {
                        SessionManager.sendMessageToSession(request,
                                "The article has been successfully created!");
                    } else {
                        SessionManager.sendMessageToSession(request,
                                "Hm, something's wrong... Please, try again later!");
                    }
                    response.sendRedirect("/my-blog/all_articles");
                    break;
                case "edit":
                    result = articleService.editArticle(article);
                    if (result) {
                        SessionManager.sendMessageToSession(request,
                                "The article has been successfully edited!");
                    } else {
                        SessionManager.sendMessageToSession(request,
                                "Hm, something's wrong... Please, try again later!");
                    }
                    response.sendRedirect("/my-blog/all_articles");
                    break;
            }
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
     */
    private void forwardMainPageWithError(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        System.out.println("calling forwardMainPageWithError");
        SessionManager.sendMessageToSession(request, e.getMessage());
        response.sendRedirect("/my-blog");
    }
}