package servlets;

import models.User;
import service.UserService;
import utils.SessionManager;
import utils.UserParser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

public class ProfileServlet extends HttpServlet {
    RequestDispatcher dispatcher;
    UserService userService = UserService.getInstance();
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (Objects.equals(action, "edit")) {
            User user = UserParser.parsUserFromRequest(request);
            boolean result = userService.editUser(user);
                if (result) {
                    SessionManager.setUserToSession(request, user);
                    dispatcher = request.getRequestDispatcher("profile.jsp");
                    dispatcher.forward(request, response);
                }
                else {
                    SessionManager.sendMessageToSession(request,
                            "The profile has not been edited! Please try again later.");
                    response.sendRedirect("/my-blog/profile");
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
}
