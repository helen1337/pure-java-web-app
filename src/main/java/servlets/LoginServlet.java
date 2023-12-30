package servlets;

import utils.LoginUtils;
import utils.SignUpUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

/*
 * Check user in session.
 * If user exists, check action == logOut:
 *   true: invoke logOut method (launch log out process);
 *   false: redirect on the main page with notification that user has already logged.
 *
 * If user doesn't exist, check action and invoke method to launch login process(tryLogin()) or signup(signUp()).
 *
 * Also in signUp() checking innerAction to launch sing up process, if innerAction doesn't exist so redirect on signUp.jsp.
 *
 * If action doesn't exist so redirect on login.jsp.
 */

public class LoginServlet extends HttpServlet {
    RequestDispatcher dispatcher;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        if (Objects.nonNull(session.getAttribute("user")))
            if (Objects.equals(action, "logOut")) logOut(request, response);
        else {
                session.setAttribute("message", "You have already logged!");
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

    private void tryLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        boolean result = LoginUtils.login(request);
        if (result) {
            response.sendRedirect("/my-blog/all_articles");
        }
        else {
            session.setAttribute("message", "Login not found or password is incorrect. Try again!");
            dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("innerAction");
        if (action != null) trySignUp(request, response);
        else {
            dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
        }
            }
    private void trySignUp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String result = SignUpUtils.signUp(request);
        switch (result) {
            case "exist" -> {
                session.setAttribute("message", "Oops, that login already exist! Please, try another one!");
                dispatcher = request.getRequestDispatcher("signup.jsp");
                dispatcher.forward(request, response);
            }
            case "fail" -> {
                session.setAttribute("message_fail", "Hm, something's wrong... Please, try again later!");
                dispatcher = request.getRequestDispatcher("signup.jsp");
                dispatcher.forward(request, response);
            }
            case "success" -> {
                session.setAttribute("message", "You have successfully logged in! Choose what to do next:");
                response.sendRedirect("/my-blog");
            }
        }
    }

    private void logOut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("message", "You are logged out!");
        response.sendRedirect("/my-blog");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
