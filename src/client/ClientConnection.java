package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection {
    private static final String HOST = "127.0.0.1";

    private final int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientConnection(int port) {
        this.port = port;
    }

    public void connect() throws IOException {
        if (socket != null) {
            return;
        }
        socket = new Socket(InetAddress.getByName(HOST), port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public String sendRequest(String request) throws IOException {
        if (socket == null) {
            throw new IllegalStateException("connect() must be called before sendRequest()");
        }
        out.println(request);
        return in.readLine();
    }

    public void close() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException ignored) {
        }
        if (out != null) {
            out.close();
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignored) {
        }
    }
}

