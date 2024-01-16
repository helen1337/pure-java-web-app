package api.server;

import utils.AppConfigurationLoader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The ForecastApiGatewayServer class represents a server for interacting with the OpenWeatherMap API
 * and providing clients with weather forecast information in JSON format.
 */
public class ForecastApiGatewayServer implements Runnable {

    /**
     * The port on which the server will listen for connections.
     */
    protected int serverPort;

    /**
     * The ServerSocket object for listening to connections.
     */
    protected ServerSocket serverSocket;

    /**
     * A flag indicating whether the server is stopped.
     */
    protected boolean isStopped = false;

    /**
     * The thread in which the server is running.
     */
    protected Thread runningThread;

    /**
     * Constructs a ForecastApiGatewayServer with the specified server port.
     *
     * <p> When restarting the server, it is important to create a new instance
     * of the ForecastApiGatewayServer class to set the default values. </p>
     *
     * @param serverPort The port on which the server will listen for connections.
     */
    public ForecastApiGatewayServer(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * Runs the server, accepting client connections and processing requests.
     */
    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped) {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    return;
                }
                e.printStackTrace();
                throw new RuntimeException("Error excepting client connection", e);
            }
            try {
                processClientRequest(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    throw new IOException("Error API server");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        System.out.println("Server stopped.");
    }

    /**
     * Processes the client's request, communicates with the OpenWeatherMap API,
     * and sends the response back to the client.
     *
     * <p> The server expects that the incoming data is information
     *  about the city for which the request needs to be made. The extracted data is used to form an HTTP GET request: </p>
     *
     * <ul>
     * <p> Gets the configuration file API switch value using AppConfigurationLoader. </p>
     * <p> Generates a request to the OpenWeatherMap API, including a city and an API switch. </p>
     * <p> Creates a new socket to connect to the OpenWeatherMap server. </p>
     * <p> Reads the response from OpenWeatherMap, storing only the JSON part (the last line of the response) in the result variable. </p>
     * <p> Closes all open streams and sockets associated with a request to OpenWeatherMap. </p>
     * <p> Sends the received JSON response back to the client via its socket.</p>
     * </ul>
     *
     * @param clientSocket The socket representing the client connection.
     * @throws IOException If an I/O error occurs.
     */
    private void processClientRequest(Socket clientSocket) throws IOException {
        // receives an input stream from a client socket
        try (InputStream inputStream = clientSocket.getInputStream();
             // creates a BufferedReader to more easily read text data from the input stream
             BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String resultJSON = null;
            // reads the first line from the input stream, assuming it is the city name sent by the client
            String city = in.readLine();
            // build a string for http request
            String remoteHost = "api.openweathermap.org";
            String apiKey = AppConfigurationLoader.getProperty("apiKey");
            String param = "/data/2.5/weather?q=" + city + "&appid=" + apiKey;
            String httpRequest = "GET " + param + " HTTP/1.0\n";
            // create new socket to connect to the API
            try (Socket socket = new Socket(remoteHost, 80);
                 PrintWriter wtr = new PrintWriter(socket.getOutputStream())) {
                wtr.println(httpRequest);
                wtr.println("Host: " + remoteHost);
                wtr.println("");
                wtr.flush();
                // creates a BufferedReader that contains the server response
                try (BufferedReader bufRead = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String response;
                    // prints each line of the response, save in result only the JSON part (the last reading)
                    while ((response = bufRead.readLine()) != null) {
                        resultJSON = response;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error inner socket", e);
            }
            // sends the received JSON response back to the client via its socket
            try (OutputStream outputStream = clientSocket.getOutputStream()) {
                outputStream.write(resultJSON.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error request socket", e);
        }
    }

    /**
     * Checks whether the server is stopped.
     *
     * @return true if the server is stopped otherwise false.
     */
    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    /**
     * Stops the server by setting the stop flag and closing the server socket.
     */
    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
            System.out.println("Server sopped");
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    /**
     * Opens the server socket for accepting client connections.
     */
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.serverPort, e);
        }
    }
}