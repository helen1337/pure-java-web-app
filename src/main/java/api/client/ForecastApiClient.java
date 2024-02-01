package api.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The ForecastApiClient class represents a client for interacting with the ForecastApiGatewayServer.
 * It is responsible for establishing a connection, sending requests, and receiving forecast data.
 */
public class ForecastApiClient {

    /** The host address to connect to. */
    private static String host;

    /** The port to connect to. */
    private static int socketPort;

    /**
     * Constructs a ForecastApiClient with the specified port and the local host.
     *
     * @see #getLocalAddress()
     * @param port The port to connect to.
     * @throws UnknownHostException If an error occurs while getting the local host address.
     */
    public ForecastApiClient(int port) throws UnknownHostException {
        host = getLocalAddress();
        socketPort = port;
    }

    /**
     * Sends a request to the API server to get JSON data for the specified city.
     *
     * @param city the name of the city for which to retrieve weather information
     * @return JSON data containing weather information for the specified city
     * @throws IOException if an I/O error occurs while communicating with the API server
     */
    public String getJSON(String city) throws IOException {
        // create the socket, send data with information about the city and read the response, assuming that this is weather forecast information in JSON format
        try (Socket clientSocket = new Socket(host, socketPort);
             OutputStream outputStream = clientSocket.getOutputStream();
             PrintWriter out = new PrintWriter(outputStream, true);
             InputStream inputStream = clientSocket.getInputStream();
             BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
                out.println(city);
                out.flush();
                outputStream.flush();
                String json = in.readLine();
                if (json == null) {
                    throw new IOException("Empty response from API server");
                }
                return json;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error getting the forecast from API server", e);
        }
    }

    /**
     * Gets the local host address.
     *
     * @return The local host address.
     * @throws UnknownHostException If an error occurs while getting the local host address.
     */
    private String getLocalAddress() throws UnknownHostException {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new UnknownHostException("Error getting local address");
        }
    }
}