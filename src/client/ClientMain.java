package client;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        ClientConnection connection = new ClientConnection(4000);
        try {
            connection.connect();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Conectado al servidor. Escriba una operacion o SALIR.");
            while (true) {
                System.out.print("Operacion: ");
                if (!scanner.hasNextLine()) {
                    break;
                }
                String request = scanner.nextLine().trim();
                if (request.isEmpty()) {
                    continue;
                }
                if (request.equalsIgnoreCase("SALIR")) {
                    break;
                }
                String response = connection.sendRequest(request);
                System.out.println("Respuesta: " + response);
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Error de conexion: " + e.getMessage());
        } finally {
            connection.close();
        }
    }
}

