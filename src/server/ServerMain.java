package server;

public class ServerMain {
    public static void main(String[] args) {
        ServerConnection server = new ServerConnection(4000);
        server.start();
    }
}
