package servlets;

import models.User;
import service.UserService;
import utils.AppConfigurationLoader;
import utils.CredentialsParser;
import utils.RegistrationParser;
import utils.SessionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Servlet handling user login, logout, and signup processes
 */
public class LoginServlet extends HttpServlet {
    RequestDispatcher dispatcher;
    UserService userService = UserService.getInstance();

    /**
     * Handles POST requests for user login, logout, and signup.
     *
     * Check user in session.
     *
     * If user isn't null, check action == logOut:
     *      true: invoke logOut method;
     *      false: redirect on the main page with notification that user has already logged.
     *
     * If user is null, check action and invoke method:
     *      if action == "tryLogin" then invoke tryLogin();
     *      if action == "signUp" then invoke signUp().
     *
     * If action is null, redirect on login.jsp.
     *
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
        if (SessionManager.isUserInSession(request))
            if (Objects.equals(action, "logOut")) logOut(request, response);
        else {
                SessionManager.sendMessageToSession(request,"You have already logged!");
                response.sendRedirect("/my-blog");
        } else {
            if (action != null) {
                switch (action) {
                    case "tryLogin" -> tryLogin(request, response);
                    case "signUp" -> signUp(request, response);
                }
            } else {
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    /**
     * Handles the login process
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException      If an input or output exception occurs
     * @throws ServletException If a servlet-specific problem occurs
     */
    private void tryLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Properties credentials = CredentialsParser.parsCredentials(request);
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
    }

    /**
     * Handles the signup process.
     * Check innerAction:
     *      if innerAction isn't null: invoke method trySignUp();
     *      otherwise redirect on signup.jsp
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
     * This method parses user registration data from the request,
     * checks if the user already exists in the database.
     * If user exists, redirect on signup.jsp with warning message,
     * otherwise try sign in the user.
     * If sign up process is successful, redirect on a main page with
     * warning message, otherwise forward back to signup page with an
     * error message.
     *
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
        SessionManager.sendMessageToSession(request,
                "You are logged out!");
        response.sendRedirect("/my-blog");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}