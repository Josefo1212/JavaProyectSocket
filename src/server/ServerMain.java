package server;

public class ServerMain {
    public static void main(String[] args) {
        OperationExecutor executor = new OperationRegistry(new Calculadora());
        ServerConnection server = new ServerConnection(4000, executor);
        server.start();
    }
}
