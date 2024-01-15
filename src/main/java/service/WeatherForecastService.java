package service;

import api.client.ForecastApiClient;
import models.WeatherForecast;
import api.server.ForecastApiGatewayServer;
import parsers.ForecastJSONParser;

import java.io.IOException;

/**
 * Service class for retrieving and searching weather forecasts.
 * This class provides methods to start and stop the API server, as well as searching for weather forecasts by city.
 */
public class WeatherForecastService {
    private ForecastApiGatewayServer server;
    private final ForecastApiClient client;
    private static WeatherForecastService instance;
    private static final int port = 9000;

    /**
     * Gets the singleton instance of WeatherForecastService.
     *
     * @throws IOException If an I/O error occurs while creating the client.
     */
    private WeatherForecastService() throws IOException {
        client = new ForecastApiClient(port);
    }
    public static WeatherForecastService getInstance() throws IOException {
        if (instance == null) {
            instance = new WeatherForecastService();
        }
        return instance;
    }

    /**
     * Searches for the weather forecast of a city.
     *
     * @param city The name of the city for which to retrieve the weather forecast.
     * @return WeatherForecast object representing the weather information for the specified city.
     * @throws IOException If an I/O error occurs while retrieving the weather forecast.
     */
    public WeatherForecast searchWeatherCity(String city) throws IOException {
        startSearchingAPI();
        String forecastJSON = client.getJSON(city);
        stopAPI();
        return ForecastJSONParser.parsJson(forecastJSON, city);
    }

    /**
     * Starts the API server for retrieving weather forecasts.
     */
    private void startSearchingAPI() {
        server = new ForecastApiGatewayServer(port);
        new Thread(server).start();
        System.out.println("Server start");
    }

    /**
     * Stops the API server.
     */
    private void stopAPI() {
        try {
            server.stop();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}