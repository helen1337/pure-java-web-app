package servlets;

import models.User;
import service.UserService;
import utils.SessionManager;
import utils.UserParser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Servlet handling user profile management processes
 */
public class ProfileServlet extends HttpServlet {
    RequestDispatcher dispatcher;
    UserService userService;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (Objects.equals(action, "edit")) {
            User user = UserParser.parsUserFromRequest(request);
            try {
                userService = UserService.getInstance();
                boolean result = userService.editUser(user);
                if (result) {
                    SessionManager.setUserToSession(request, user);
                    dispatcher = request.getRequestDispatcher("profile.jsp");
                    dispatcher.forward(request, response);
                } else {
                    SessionManager.sendMessageToSession(request,
                            "The profile has not been edited! Please try again later.");
                    response.sendRedirect("/my-blog/profile");
                }
            } catch (SQLException | ClassNotFoundException e) {
                forwardMainPageWithError(request, response, e);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (!SessionManager.isUserInSession(request)) {
            SessionManager.sendMessageToSession(request,
                    "You have not been logged in!");
            response.sendRedirect("/my-blog/all_articles");
        }
        else {
            String action = request.getParameter("action");
            if (action != null) {
                switch (action) {
                    case "edit":
                        editProfile(request, response);
                        break;
                    case "delete":
                        deleteProfile(request, response);
                }
            }
            else forwardProfile(request, response);
        }
    }

    private void forwardProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }

    private void editProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("action", "edit");
        String nextJSP = "/profile.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void deleteProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = SessionManager.getUserFromSession(request);
        boolean result = SessionManager.isUserInSession(request) && userService.deleteUser(user);
        if (result) {
            SessionManager.deleteUserFromSession(request);
            SessionManager.sendMessageToSession(request,
                    "Profile has been deleted successfully!");
            response.sendRedirect("/my-blog");
        }
        else {
            SessionManager.sendMessageToSession(request,
                    "Hm, something's wrong... Please, try again later!");
            response.sendRedirect("/my-blog/profile");
        }
    }

    /**
     * Forwards the request to the main page (/my-blog) with an error message
     * stored in the session.
     *
     * This method is used when an exception occurs and need to notify the user
     * about the error by storing the error message in the session and
     * redirecting them to the main page.
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
}
