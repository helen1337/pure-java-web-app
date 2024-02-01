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
 * Servlet for user profile management processes, including editing and deletion.
 */
public class ProfileServlet extends HttpServlet {
    RequestDispatcher dispatcher;
    UserService userService;

    /**
     * Handles POST requests for editing user profiles.
     * <p> Parses the user information from the request, edits the user profile
     * using UserService, and updates the session with the modified user. </p>
     * Forwards to the profile page ("/profile.jsp") if successful,
     * otherwise redirects to the profile page with an error message.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
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
                forwardPageWithError(request, response, e);
            }
        }
    }

    /**
     * Handles GET requests for viewing and managing user profiles.
     * <p> Checks if the user is logged in. </p>
     * <ul> If not, redirects to the article list page ("/my-blog/all_articles") with the message.
     * <p> If logged in, determines the requested action (edit, delete, or view) and performs
     * the corresponding action by calling the methods. </p>
     * <p> If an invalid action is specified, forwards to the profile page with a default action.</p>
     * </ul>
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException      If an input or output exception occurs
     * @throws ServletException If a servlet-specific problem occurs
     */
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

    /**
     * Forwards the request to the profile page ("/profile.jsp").
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void forwardProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Sets the "action" parameter of the request to "edit" and forwards to the profile page ("/profile.jsp") for editing
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException      If an input or output exception occurs
     * @throws ServletException If a servlet-specific problem occurs
     */
    private void editProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("action", "edit");
        String nextJSP = "/profile.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    /**
     * Deletes the user profile and redirects to the main page ("/my-blog") if successful,
     * otherwise redirects to the profile page with an error message.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException If an input or output exception occurs
     */
    private void deleteProfile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = SessionManager.getUserFromSession(request);
        try {
            userService = UserService.getInstance();
            boolean result = SessionManager.isUserInSession(request) && userService.deleteUser(user);
            if (result) {
                SessionManager.deleteUserFromSession(request);
                SessionManager.sendMessageToSession(request,
                        "Profile has been deleted successfully!");
                response.sendRedirect("/my-blog");
            } else {
                SessionManager.sendMessageToSession(request,
                        "Hm, something's wrong... Please, try again later!");
                response.sendRedirect("/my-blog/profile");
            }
        } catch (SQLException | ClassNotFoundException e) {
            forwardPageWithError(request, response, e);
        }
    }

    /**
     * <p> This method is used when an exception occurs and need to notify the user
     * about the error by storing the error message in the session and
     * redirecting them to the next page.</p>
     *
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param e The exception that occurred, providing additional information about the error
     * @throws IOException If an input or output exception occurs
     *
     * @see SessionManager#sendMessageToSession(HttpServletRequest, String)
     */
    private void forwardPageWithError(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException, ServletException {
        SessionManager.sendMessageToSession(request, e.getMessage());
        forwardProfile(request, response);
    }
}