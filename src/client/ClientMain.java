package client;

import com.google.gson.JsonElement;
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
                System.out.println("Enviando JSON al servidor: " + request);

                String response = connection.sendRequest(request);

                if (response == null) {
                    System.out.println("Sin respuesta del servidor.");
                } else {
                    RespuestaDto respuestaProcesada = ProtocoloClient.deserializarRespuesta(response);
                    if (respuestaProcesada == null) {
                        System.out.println("Respuesta invalida del servidor.");
                        continue;
                    }

                    if ("OK".equalsIgnoreCase(respuestaProcesada.getEstado())) {
                        JsonElement resultado = respuestaProcesada.getResultado();
                        System.out.println("Resultado servidor: " + (resultado == null ? "null" : resultado.toString()));
                    } else {
                        System.err.println("Error servidor: " + respuestaProcesada.getMensaje());
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