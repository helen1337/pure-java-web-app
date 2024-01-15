package servlets;

import models.User;
import service.UserService;
import utils.CredentialsParser;
import utils.RegistrationParser;
import utils.SessionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

/**
 * Servlet handling user login, logout, and signup processes
 */
public class LoginServlet extends HttpServlet {
    RequestDispatcher dispatcher;
    UserService userService;

    /**
     * Handles POST requests for user login, logout, and signup.
     *
     * <p>This method checks if a user is in the session:</p>
     * <ul>
     *     If the user is in the session, it checks if the action is "logOut":
     * <ul>
     *     <li>If true, it invokes the logOut method.</li>
     *     <li>If false, it redirects to the main page with a notification
     *     that the user is already logged in.</li>
     * </ul>
     *
     * <p>If the user isn't in the session, it checks the action and invokes the corresponding method:</p>
     *
     * <ul>
     *     <li>If the action is "tryLogin," it invokes tryLogin.</li>
     *     <li>If the action is "signUp," it invokes signUp.</li>
     *     <li>If the action is null, it redirects to login.jsp.</li>
     * </ul>
     *
     * </ul>
     *
     * @see UserService#getInstance()
     * @see #signUp(HttpServletRequest, HttpServletResponse)
     * @see #tryLogin(HttpServletRequest, HttpServletResponse)
     * @see #logOut(HttpServletRequest, HttpServletResponse)
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws IOException      If an input or output exception occurs.
     * @throws ServletException If a servlet-specific problem occurs.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");

        if (SessionManager.isUserInSession(request)) {
            if (Objects.equals(action, "logOut")) {
                logOut(request, response);
            }
            else {
                SessionManager.sendMessageToSession(request, "You have already logged!");
                response.sendRedirect("/my-blog");
            }
        }
        else {
            if (action != null) {
                switch (action) {
                    case "tryLogin" -> tryLogin(request, response);
                    case "signUp" -> signUp(request, response);
                }
            }
            else {
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    /**
     * Handles the login process.
     *
     * <p>This method parses user credentials data from the request.</p>
     *
     * <p>If the user login is successful, the user is set in the session,
     * and the response is redirected to "/my-blog/all_articles".</p>
     * <p>If the login fails, an error message is stored in the session,
     * and the user is forwarded to the login page to try again.</p>
     *
     * <p>This method uses a try-catch block to handle potential exceptions
     * that may occur while working with the database by calling a method
     * that generates an error message and redirects the user to the main page
     * with an alert message.</p>
     *
     * @see #forwardMainPageWithError(HttpServletRequest, HttpServletResponse, Exception)
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException      If an input or output exception occurs
     * @throws ServletException If a servlet-specific problem occurs
     */
    private void tryLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Properties credentials = CredentialsParser.parsCredentials(request);
        try {
            userService = UserService.getInstance();
            User user = userService.login(credentials);
            if (Objects.nonNull(user)) {
                SessionManager.setUserToSession(request, user);
                response.sendRedirect("/my-blog/all_articles");
            }
            else {
                SessionManager.sendMessageToSession(request,"Login not found or password is incorrect. Try again!");
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            forwardMainPageWithError(request, response, e);
        }
    }

    /**
     * Handles the signup process.
     * <ul>
     * <p>Check innerAction:</p>
     * <ul>
     * <li>If innerAction isn't null: invoke method trySignUp();</li>
     * <li>Otherwise redirect to signup.jsp.</li>
     * </ul>
     * </ul>
     *
     * @see #trySignUp(HttpServletRequest, HttpServletResponse)
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void signUp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("innerAction");
        if (action != null) {
            trySignUp(request, response);
        } else {
            dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Handles the inner signup process.
     *
     * <p>This method parses user registration data from the request,
     * checks if the user already exists in the database.</p>
     *
     * <p>If the user exists, redirects to signup.jsp with a warning message,
     * otherwise tries to sign in the user.</p>
     *
     * <p>If the sign-up process is successful, redirects to the main page with
     * a success message, otherwise forwards back to the signup page with an
     * error message.</p>
     *
     * <p>This method uses a try-catch block to handle potential exceptions
     * that may occur while working with the database by calling a method
     * that generates an error message and redirects the user to the main page
     * with an alert message.</p>
     *
     * @see #forwardMainPageWithError(HttpServletRequest, HttpServletResponse, Exception)
     * @see RegistrationParser#parsRegisterUser(HttpServletRequest)
     * @see UserService#isUserExist(User)
     * @see UserService#signUp(User)
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void trySignUp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = RegistrationParser.parsRegisterUser(request);
        try {
            userService = UserService.getInstance();
            if (userService.isUserExist(user)) {
                SessionManager.sendMessageToSession(request,
                        "Oops, that login already exist! Please, try another one!");
                dispatcher = request.getRequestDispatcher("signup.jsp");
                dispatcher.forward(request, response);
            }
            else {
                boolean result = userService.signUp(user);
                if (result) {
                    SessionManager.sendMessageToSession(request,
                            "You have successfully logged in! Choose what to do next:");
                    response.sendRedirect("/my-blog");
                }
                else {
                    SessionManager.sendMessageToSession(request, "Hm, something's wrong... Please, try again later!");
                    dispatcher = request.getRequestDispatcher("signup.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            forwardMainPageWithError(request, response, e);
        }
    }

    /**
     * Handles the logout process.
     * Delete user from the session and redirect to a main page with the message.
     *
     * @see SessionManager
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException If an input or output exception occurs
     */
    private void logOut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        SessionManager.deleteUserFromSession(request);
        SessionManager.sendMessageToSession(request, "You are logged out!");
        response.sendRedirect("/my-blog");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}