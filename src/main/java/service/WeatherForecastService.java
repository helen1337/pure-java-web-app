package service;

import models.WeatherForecast;
import parsers.WeatherJSONParser;

import java.io.IOException;

public class WeatherForecastService {

    public static WeatherForecast searchWeatherCity(String city) throws IOException {
        return WeatherJSONParser.parsJsonByCity(city);
    }
}
