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

                double a = Double.parseDouble(firstText);
                double b = Double.parseDouble(secondText);
                Double localResult = calculate(operation, a, b);
                if (localResult == null) {
                    System.out.println("Operacion invalida o division entre cero.");
                    continue;
                }
                System.out.println("Resultado: " + localResult);

                String request = operation + " " + firstText + " " + secondText;
                String response = connection.sendRequest(request);
                if (response == null) {
                    System.out.println("Sin respuesta del servidor.");
                } else {
                    System.out.println("Respuesta servidor: " + response);
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

    private static Double calculate(String operation, double a, double b) {
        String op = operation.trim().toUpperCase();
        switch (op) {
            case "SUMA":
            case "+":
                return a + b;
            case "RESTA":
            case "-":
                return a - b;
            case "MULT":
            case "*":
                return a * b;
            case "DIV":
            case "/":
                if (b == 0.0) {
                    return null;
                }
                return a / b;
            default:
                return null;
        }
    }
}
