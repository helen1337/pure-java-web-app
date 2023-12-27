package servlets;

import utils.ProfileUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

public class ProfileServlet extends HttpServlet {
    RequestDispatcher dispatcher;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        if (action!=null) {
        switch (action) {
            case "edit":
                boolean result = ProfileUtils.editProfile(request);
                if (result) {
                    dispatcher = request.getRequestDispatcher("profile.jsp");
                    dispatcher.forward(request, response);

                } else {
                    session.setAttribute("message", "The profile has not been edited! Please try again later.");
                    response.sendRedirect("/my-blog/profile");
                } break;
        }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (Objects.isNull(session.getAttribute("user"))) {
            session.setAttribute("message", "You have not been logged in!");
            response.sendRedirect("/my-blog/all_articles");
        } else {
            String action = request.getParameter("action");
            if (action != null) {
                switch (action) {
                    case "edit":
                        editProfile(request, response, session);
                        break;
                    case "delete":
                        deleteProfile(request, response, session);
                }
            } else forwardProfile(request, response, session);
        }
    }

    private void forwardProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }

    private void editProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException {
        request.setAttribute("action", "edit");
        String nextJSP = "/profile.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void deleteProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        boolean result = ProfileUtils.deleteProfile(request);
        if (result) {
            session.setAttribute("message", "Profile has been deleted successfully!");
            response.sendRedirect("/my-blog");

        } else {
            session.setAttribute("message", "Hm, something's wrong... Please, try again later!");
            response.sendRedirect("/my-blog/profile");
        }

    }

}
