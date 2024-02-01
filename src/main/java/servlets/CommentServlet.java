package servlets;

import models.Comment;
import service.CommentService;
import utils.CommentParser;
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

/**
 * Servlet implementation for handling comments related operations.
 */
public class CommentServlet extends HttpServlet {

    /**
     * Handles HTTP GET requests to retrieve and display comments for a specific article.
     *
     * <p>
     *     This method uses a try-catch block to handle potential exceptions
     *     that may occur while working with the database by calling a method
     *     that generates an error message and redirects the user to the main page
     *     with an alert message.
     * </p>
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If an exception occurs while processing the request
     * @throws IOException      If an input or output exception occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String article_id = request.getParameter("id");
        try {
            CommentService commentService = CommentService.getInstance();
            List<Comment> commentsList = commentService.getComments(article_id);
            request.setAttribute("commentsList", commentsList);
            RequestDispatcher dispatcher = getServletContext().
                    getRequestDispatcher("/WEB-INF/blocks/comment.jsp");
            dispatcher.include(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            forwardMainPageWithError(request, response, e);
        }
    }

    /**
     * Handles HTTP POST requests to perform comment-related action (send or delete).
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If an exception occurs while processing the request
     * @throws IOException      If an input or output exception occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("commAction");
        if (!StringUtils.isEmpty(action)) {
            switch (action) {
                case "send" -> sendComment(request, response);
                case "delete" -> deleteComment(request, response);
            }
        }
    }

    /**
     * Handles sending a comment and redirects the user to the article page with a
     * message about the result.
     *
     * <p>
     *     This method uses a try-catch block to handle potential exceptions
     *     that may occur while working with the database by calling a method
     *     that generates an error message and redirects the user to the main page
     *     with an alert message.
     * </p>
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException If an input or output exception occurs
     */
    private void sendComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String article_id = request.getParameter("article_id");
        Comment comment = CommentParser.parsComment(request);
        try {
            CommentService commentService = CommentService.getInstance();
            boolean result = commentService.addComment(comment);
            if (result) {
                SessionManager.sendMessageToSession(request,
                        "The comment has been published successfully!");
            }
            else {
                SessionManager.sendMessageToSession(request,
                        "Hm, something's wrong... Please, try again later!");
            }
            response.sendRedirect("/my-blog/article?id=" + article_id);
        } catch (SQLException | ClassNotFoundException e) {
            forwardMainPageWithError(request, response, e);
        }
    }

    /**
     * Handles deleting a comment and redirects the user to the article page with a
     * message about the result.
     *
     * <p>
     *     This method uses a try-catch block to handle potential exceptions
     *     that may occur while working with the database by calling a method
     *     that generates an error message and redirects the user to the main page
     *     with an alert message.
     * </p>
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException If an input or output exception occurs
     */
    private void deleteComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String comment_id = request.getParameter("comment_id");
        String article_id = request.getParameter("article_id");
        try {
            CommentService commentService = CommentService.getInstance();
            boolean result = commentService.deleteComment(comment_id);
            if (result) {
                SessionManager.sendMessageToSession(request,
                        "The comment has been deleted succeessfully!");
            }
            else {
                SessionManager.sendMessageToSession(request,
                        "Hm, something's wrong... Please, try again later!");
            }
            response.sendRedirect("/my-blog/article?id=" + article_id);
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