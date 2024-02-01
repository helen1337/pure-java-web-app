package utils;

import models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility class for parsing user information from a HttpServletRequest
 */
public class UserParser {

    /**
     * Parses user information from the HttpServletRequest parameters and the session
     *
     * @param request The HttpServletRequest containing user-related parameters
     * @return A User object populated with information from the request parameters and the session
     */
    public static User parsUserFromRequest(HttpServletRequest request) {
        // Retrieve the user object from the session
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        // Extract user-related information from the request parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        // Extract user ID and login from the sessionUser
        String user_id = Integer.toString(sessionUser.getUser_id());
        String login = sessionUser.getLogin();
        // Create a new User object and set its properties
        User user = new User();
        user.setUser_id(Integer.parseInt(user_id));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setCity(city);

        return user;
    }
}