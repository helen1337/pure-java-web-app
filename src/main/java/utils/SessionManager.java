package utils;

import models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Utility class for managing user sessions in a web application
 */
public class SessionManager {

    /**
     * Adds a message to the session attribute "message"
     *
     * @param request The HttpServletRequest associated with the user session
     * @param message The message to be added to the session
     */
    public static void sendMessageToSession(HttpServletRequest request, String message) {
        HttpSession session = request.getSession();
        session.setAttribute("message", message);
    }

    /**
     * Checks if a user is present in the session
     *
     * @param request The HttpServletRequest associated with the user session
     * @return true if a user is present in the session otherwise false
     */
    public static boolean isUserInSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Objects.nonNull(session.getAttribute("user"));
    }

    /**
     * Sets the user in the session attribute "user"
     *
     * @param request The HttpServletRequest associated with the user session
     * @param user The User object to be set in the session
     */
    public static void setUserToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }

    /**
     * Retrieves the user from the session
     *
     * @param request The HttpServletRequest associated with the user session
     * @return The User object stored in the session, or null if no user is present
     */
    public static User getUserFromSession(HttpServletRequest request) {
        if (!isUserInSession(request)) return null;
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }

    /**
     * Removes the user from the session
     *
     * @param request The HttpServletRequest associated with the user session
     */
    public static void deleteUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
    }
}