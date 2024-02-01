package utils;

import models.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for parsing user registration information from a HttpServletRequest
 */
public class RegistrationParser {

    /**
     * Parses user registration information from the HttpServletRequest parameters.
     *
     * @param request The HttpServletRequest containing user registration parameters
     * @return A User object populated with information from the request parameters
     */
    public static User parsRegisterUser(HttpServletRequest request) {
        String login = request.getParameter("login");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setCity(city);
        return newUser;
    }
}