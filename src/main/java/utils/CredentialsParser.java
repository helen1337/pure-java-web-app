package utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * Utility class for parsing login credentials from a HttpServletRequest
 */
public class CredentialsParser {

    /**
     * Parses login credentials from the HttpServletRequest parameters
     *
     * @param request The HttpServletRequest containing login-related parameters
     * @return A Properties object containing login credentials with keys "login"
     * and "password"
     */
    public static Properties parsCredentials(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Properties credentials = new Properties();
        credentials.setProperty("login", login);
        credentials.setProperty("password", password);
        return credentials;
    }
}
