package apiServers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ForecastApiGatewayServer implements Runnable{
    protected int serverPort;
    protected ServerSocket serverSocket;
    protected boolean isStopped = false;
    protected Thread runningThread;

    public ForecastApiGatewayServer(int serverPort) {
        this.serverPort = serverPort;
    }

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
                throw new RuntimeException("Error eccepting client connetion", e);
            }
            try {
                processClientRequest(clientSocket);
            } catch (Exception e) {
                e.printStackTrace();
                //g2 next req
            }
        }
        System.out.println("Server stopped.");
        }

    private void processClientRequest(Socket clientSocket) throws IOException {

        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String city = in.readLine();
        String remoteHost = "api.openweathermap.org";
        String param = "/data/2.5/weather?q=" + city + "&appid=1e3eadbf60c0cf947bf23de09ac84bdf";
        String httpRequest = "GET " + param + " HTTP/1.0\n";
        Socket socket;

        //Instantiate a new socket for the request
        try {
            socket = new Socket(remoteHost, 80);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port on api.openweathermap.org:80", e);
        }

        //Instantiates a new PrintWriter passing in the sockets output stream
        try {
            PrintWriter wtr = new PrintWriter(socket.getOutputStream());

            //Prints the request string to the output stream
            wtr.println(httpRequest);
            wtr.println("Host: " + remoteHost);
            wtr.println("");
            wtr.flush();

            //Creates a BufferedReader that contains the server response
            BufferedReader bufRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response;
            String result = null;

            //Prints each line of the response, save in result only the JSON part (the last reading)
            while ((response = bufRead.readLine()) != null) {
                System.out.println(response);
                result = response;
            }
            System.out.println("response was getting");

            //Closes out buffer and writer
            bufRead.close();
            wtr.close();
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException("Error closing inner socket", e);
            }

            //Send the response to client
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(result.getBytes());
            inputStream.close();
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException("Error in stream the request socket", e);
        }


    }
    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}
