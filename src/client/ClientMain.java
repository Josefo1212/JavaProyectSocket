package client;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        ClientConnection connection = new ClientConnection(4000);
        InterfazUsuario interfaz = new InterfazUsuario();
        try {
            connection.connect();
            System.out.println("Conectado al servidor. Operaciones: SUMA, RESTA, MULT, DIV. Escriba SALIR para terminar.");

            while (true) {
                String operation = interfaz.pedirOperacion();
                if (operation.isEmpty()) {
                    continue;
                }
                if (operation.equalsIgnoreCase("SALIR")) {
                    break;
                }

                String firstText = interfaz.pedirNumero("Primer numero (A): ");
                String secondText = interfaz.pedirNumero("Segundo numero (B): ");

                if (!isValidNumber(firstText) || !isValidNumber(secondText)) {
                    System.out.println("Entrada invalida. Use numeros reales (ej: 10, -3.5).");
                    continue;
                }

                String request = ProtocoloClient.serializarPeticion(operation, firstText, secondText);
                System.out.println("Enviando trama al servidor: " + request);

                String response = connection.sendRequest(request);

                if (response == null) {
                    System.out.println("Sin respuesta del servidor.");
                } else {
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
        } catch (IOException e) {
            System.err.println("Error de conexion: " + e.getMessage());
        } finally {
            interfaz.cerrar();
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