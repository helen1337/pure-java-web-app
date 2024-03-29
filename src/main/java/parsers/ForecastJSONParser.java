package parsers;

import models.WeatherForecast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The ForecastJSONParser class is responsible for parsing JSON data retrieved by city name
 * and returning a WeatherForecast object.
 */
public class ForecastJSONParser {

    /**
     * Parses JSON data retrieved by city name and returns a WeatherForecast object
     *
     * @param city the name of the city for which to retrieve weather information
     * @return WeatherForecast object representing the parsed weather information
     * @throws RuntimeException if the JSON is empty or has an error code
     */
    public static WeatherForecast parsJson(String JSON, String city) throws RuntimeException {
        if (JSON == null || JSON.isEmpty()) {
            System.out.printf("No raw data%n");
            throw new RuntimeException("No response from weather server");
        }
        JSONObject forecastResponse = new JSONObject(JSON);
        if (forecastResponse.getInt("cod") != 200) {
            throw new RuntimeException("Invalid request to the weather server");
        }
        // Longitude
        double longitude = forecastResponse.getJSONObject("coord").getDouble("lon");
        // Latitude
        double latitude = forecastResponse.getJSONObject("coord").getDouble("lat");
        //Weather
        //Clarification
        String weatherParam = null;
        String additionWeatherParams = null;
        JSONArray weather = forecastResponse.getJSONArray("weather");
        for (int i = 0; i < weather.length(); i++) {
            JSONObject weatherValue = weather.getJSONObject(i);
            weatherParam = weatherValue.getString("main");
            additionWeatherParams = weatherValue.getString("description");
        }
        //Temperature, Celsius
        double temp_K = forecastResponse.getJSONObject("main").getDouble("temp");
        String temp = new DecimalFormat("#0.0").format(converterK_C(temp_K));
        //Temperature feels like, Celsius
        double tempFeelsLike_K = forecastResponse.getJSONObject("main").getDouble("feels_like");
        String tempFeelsLike = new DecimalFormat("#0.0").format(converterK_C(tempFeelsLike_K));
        //Atmospheric pressure on the sea level, hPa
        //Humidity %
        double atmPressure = forecastResponse.getJSONObject("main").getDouble("pressure");
        double humidity = forecastResponse.getJSONObject("main").getDouble("humidity");
        //Visibilty, km
        double visibility = forecastResponse.getDouble("visibility");
        //Wind speed, m/s
        double windSpeed = forecastResponse.getJSONObject("wind").getDouble("speed");
        //Wind direction, degrees
        double windDeg;
        String windDir = "-";
        if (windSpeed != 0) {
            windDeg = forecastResponse.getJSONObject("wind").getDouble("deg");
            windDir = windDirection(windDeg);
        }
        //Rain volume for the last 1 hour, mm
        String rain = "-";
        try {
            rain = String.valueOf(forecastResponse.getJSONObject("rain").getDouble("1h"));
            System.out.println("Rain volume for the last 1 hour, mm: " + rain);
        } catch (JSONException e) {
            System.out.println("Field 'rain' doesn't exist");
        }
        String snow = "-";
        //Snow volume for the last 1 hour, mm
        try {
            snow = String.valueOf(forecastResponse.getJSONObject("snow").getDouble("1h"));
            System.out.println("Snow volume for the last 1 hour, mm: " + snow);
        } catch (JSONException e) {
            System.out.println("Field 'snow' doesn't exist");
        }
        //sunriseTime
        int sunriseTimeUnix = forecastResponse.getJSONObject("sys").getInt("sunrise");
        Date sunriseTimeData = new Date(sunriseTimeUnix * 1000L);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String sunriseTime = formatter.format(sunriseTimeData);
        //sunsetTime
        int sunsetTimeUnix = forecastResponse.getJSONObject("sys").getInt("sunset");
        Date sunsetTimeData = new Date(sunsetTimeUnix * 1000L);
        String sunsetTime = formatter.format(sunsetTimeData);
        return WeatherForecast.newBuilder().setCity(city).setLongitude(longitude)
                .setLatitude(latitude).setWeatherParam(weatherParam)
                .setAdditionWeatherParams(additionWeatherParams)
                .setTemp(temp).setTempFeelsLike(tempFeelsLike)
                .setAtmPressure(atmPressure).setHumidity(humidity)
                .setVisibility(visibility).setWindSpeed(windSpeed)
                .setWindDir(windDir).setRain(rain).setSnow(snow)
                .setSunriseTime(sunriseTime).setSunsetTime(sunsetTime)
                .build();
    }

    /**
     * Converts temperature from Kelvin to Celsius
     *
     * @param temp_K the temperature in Kelvin
     * @return temperature in Celsius
     */
    private static double converterK_C(double temp_K) {
        return (temp_K - 273.15);
    }

    /**
     * Determines wind direction based on degrees
     *
     * @param degrees wind direction in degrees
     * @return Wind direction as a cardinal point (N, NE, E, SE, S, SW, W, NW)
     */
    private static String windDirection(double degrees) {
        if (degrees >=350  && degrees <= 360 || degrees <= 10 && degrees >= 0) return "N";
        if (degrees > 10 && degrees < 80) return "NE";
        if (degrees >= 80 && degrees <= 100) return "E";
        if (degrees > 100 && degrees < 170) return "SE";
        if (degrees >= 170 && degrees <= 190) return "S";
        if (degrees > 190 && degrees < 260) return "SW";
        if (degrees >= 260 && degrees <= 280) return "W";
        else return "NW";
    }
}