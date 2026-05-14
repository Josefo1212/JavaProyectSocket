package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {
    private static final String HOST = "127.0.0.1";

    private final int port;

    public ServerConnection(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port, 1, InetAddress.getByName(HOST))) {
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String line;
                while ((line = in.readLine()) != null) {
                    out.println("ACK");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
