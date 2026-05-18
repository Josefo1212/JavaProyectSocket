package client;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        ClientConnection connection = new ClientConnection(4000);
        try {
            connection.connect();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Conectado al servidor. Operaciones: SUMA, RESTA, MULT, DIV. Escriba SALIR para terminar.");

            while (true) {
                System.out.print("Operacion: ");
                if (!scanner.hasNextLine()) {
                    break;
                }
                String operation = scanner.nextLine().trim();
                if (operation.isEmpty()) {
                    continue;
                }
                if (operation.equalsIgnoreCase("SALIR")) {
                    break;
                }

                System.out.print("Primer numero (A): ");
                if (!scanner.hasNextLine()) {
                    break;
                }
                String firstText = scanner.nextLine().trim();

                System.out.print("Segundo numero (B): ");
                if (!scanner.hasNextLine()) {
                    break;
                }
                String secondText = scanner.nextLine().trim();

                if (!isValidNumber(firstText) || !isValidNumber(secondText)) {
                    System.out.println("Entrada invalida. Use numeros reales (ej: 10, -3.5).");
                    continue;
                }

                // 1. SERIALIZACIÓN: Armamos la trama custom (Ej: "SUMA|10|5")
                String request = ProtocoloClient.serializarPeticion(operation, firstText, secondText);
                System.out.println("Enviando trama al servidor: " + request);

                // 2. TRANSMISIÓN: Envío a través del socket
                String response = connection.sendRequest(request);

                if (response == null) {
                    System.out.println("Sin respuesta del servidor.");
                } else {
                    // 3. DESERIALIZACIÓN: Rompemos la respuesta usando el puente (Ej: "OK|15.0")
                    String[] respuestaProcesada = ProtocoloClient.deserializarRespuesta(response);
                    String estado = respuestaProcesada[0];
                    String contenido = respuestaProcesada[1];

                    if (estado.equals("OK")) {
                        System.out.println("Resultado servidor: " + contenido);
                    } else {
                        System.err.println("Error servidor: " + contenido);
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Error de conexion: " + e.getMessage());
        } finally {
            connection.close();
        }
    }

    private static boolean isValidNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}