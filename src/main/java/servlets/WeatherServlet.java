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

public class WeatherServlet extends HttpServlet {

    WeatherForecast weatherForecast;

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

    private void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextJSP = "/weather.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void searchForUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = SessionManager.getUserFromSession(request);
        String searchCity = user.getCity();
        weatherForecast = WeatherForecastService.searchWeatherCity(searchCity);
        if (Objects.nonNull(weatherForecast)) {
            request.setAttribute("weatherForecast", weatherForecast);
        }
        else {
            SessionManager.sendMessageToSession(request,
                    "City not found or weather API not access now");
        }
        forwardPage(request, response);
    }

    private void searchByCity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchCity = request.getParameter("city");
        weatherForecast = WeatherForecastService.searchWeatherCity(searchCity);
        System.out.println(searchCity);
        if (Objects.nonNull(weatherForecast)) {
            request.setAttribute("weatherForecast", weatherForecast);
        }
        else {
            SessionManager.sendMessageToSession(request,
                    "City not found or weather API not access now");
        }
        forwardPage(request, response);
    }
}
