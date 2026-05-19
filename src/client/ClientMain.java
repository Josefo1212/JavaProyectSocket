package client;

import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

                String cantidadTexto = interfaz.pedirCantidadParametros();
                if (!isValidInteger(cantidadTexto)) {
                    System.out.println("Entrada invalida. Use un entero (ej: 2, 3, 4)." );
                    continue;
                }

                int cantidad = Integer.parseInt(cantidadTexto);
                if (cantidad < 2) {
                    System.out.println("Se requieren al menos 2 parametros.");
                    continue;
                }

                List<String> valores = new ArrayList<>();
                boolean invalido = false;
                for (int i = 1; i <= cantidad; i++) {
                    String texto = interfaz.pedirNumero("Parametro #" + i + ": ");
                    if (!isValidNumber(texto)) {
                        System.out.println("Entrada invalida. Use numeros reales (ej: 10, -3.5).");
                        invalido = true;
                        break;
                    }
                    valores.add(texto);
                }
                if (invalido) {
                    continue;
                }

                String request = ProtocoloClient.serializarPeticion(operation, valores);
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

    private static boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}