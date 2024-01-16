package servlets;

import models.User;
import models.WeatherForecast;
import service.WeatherForecastService;
import utils.SessionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * WeatherServlet handles weather forecast requests, supporting user session-based
 * or city-specific searches.
 */
public class WeatherServlet extends HttpServlet {

    WeatherForecast weatherForecast;
    WeatherForecastService weatherForecastService;

    /**
     * Handles GET requests. Checks the "action" parameter and performs the
     * appropriate action:
     * <ul>
     *     <p> If no action is specified, checks if the user is in the session:</p>
     *     <ul>
     *         <li> If yes, retrieves the user's city and searches
     *         for the weather forecast. </li>
     *         <li> If not, forwards to the weather page. </li>
     *     </ul>
     *         If the "action" parameter is specified, searches for the weather forecast
     *         based on the specified city.
     * </ul>
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (Objects.isNull(action)) {
            if (!SessionManager.isUserInSession(request)) {
                forwardPage(request, response);
            }
            else searchForUser(request, response);
        }
        else {
            searchByCity(request, response);
        }
    }

    /**
     * Forwards the request to the weather page ("/weather.jsp").
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextJSP = "/weather.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    /**
     * Searches for the weather forecast based on the user's city stored in the session.
     * <p> If the weather forecast is found, it sets the forecast in the request
     * attribute for rendering on the JSP page. </p>
     * If not, it stores an error message in the session.
     * Any exceptions during the process are handled by forwarding to the error page.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void searchForUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = SessionManager.getUserFromSession(request);
        try {
            String searchCity = user.getCity();
            weatherForecastService = WeatherForecastService.getInstance();
            weatherForecast = weatherForecastService.searchWeatherCity(searchCity);
            if (Objects.nonNull(weatherForecast)) {
                request.setAttribute("weatherForecast", weatherForecast);
            } else {
                SessionManager.sendMessageToSession(request,
                        "City not found or weather API not access now");
            }
        } catch (Exception e) {
            forwardPageWithError(request, response, e);
        }
        forwardPage(request, response);
    }

    /**
     * Searches for the weather forecast based on the specified city.
     * <p> If the weather forecast is found, it sets the forecast in the request
     * attribute for rendering on the JSP page. If not, it stores an error message
     * in the session.</p>
     * Any exceptions during the process are handled by forwarding to the error page.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific problem occurs
     * @throws IOException      If an input or output exception occurs
     */
    private void searchByCity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchCity = request.getParameter("city");
        try {
            weatherForecastService = WeatherForecastService.getInstance();
            weatherForecast = weatherForecastService.searchWeatherCity(searchCity);
            if (Objects.nonNull(weatherForecast)) {
                request.setAttribute("weatherForecast", weatherForecast);
            } else {
                SessionManager.sendMessageToSession(request,
                        "City not found or weather API not access now");
            }
        } catch (Exception e) {
            forwardPageWithError(request, response, e);
        }
        forwardPage(request, response);
    }

    /**
     * Forwards the request to the weather page (/weather.jsp) with an error message
     * stored in the session.
     *
     * This method is used when an exception occurs and need to notify the user
     * about the error by storing the error message in the session and
     * redirecting them to the main page.
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
        forwardPage(request, response);
    }
}