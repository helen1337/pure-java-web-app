package requester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A utility class for making weather API requests
 */
public class WeatherRequester {

    /**
     * Sends a weather request to the OpenWeatherMap API for a specified city
     *
     * @param searchCity the city for which to request weather information.
     * @return The JSON response string containing weather information, or null if the request fails.
     * @throws IOException If an I/O error occurs while making the request.
     */
    public static String weatherRequest(String searchCity) throws IOException {
        String apiEndPoint = "https://api.openweathermap.org/data/2.5/weather?q=";
        String city = searchCity;
        String apiKey = "1e3eadbf60c0cf947bf23de09ac84bdf";
        // build the URL pieces
        StringBuilder requestBuilder = new StringBuilder(apiEndPoint);
        requestBuilder.append(city).append("&appid=").append(apiKey);
        // set up the connection
        URL url = new URL(requestBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        // check the response code and set up the reader for the appropriate stream
        int responseCode = conn.getResponseCode();
        boolean isSuccess = responseCode == 200;
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                isSuccess ? conn.getInputStream() : conn.getErrorStream()))) {
            // read the response
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (!isSuccess) {
            return null;
        }
        // pass the string response for parsing and using
        // return Obj
        return response.toString();
    }
}
