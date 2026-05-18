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
            System.out.println("Servidor Java escuchando en el puerto " + port + "...");
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Llegó trama cruda al servidor: " + line);

                    // 1. DESERIALIZACIÓN: Desarmamos la trama usando tu puente de servidor
                    String[] partes = ProtocoloServer.deserializarPeticion(line);

                    if (partes == null || partes.length < 3) {
                        out.println(ProtocoloServer.serializarRespuesta("ERROR", "Trama incompleta o invalida"));
                        continue;
                    }

                    String operacion = partes[0];
                    String aText = partes[1];
                    String bText = partes[2];

                    try {
                        double a = Double.parseDouble(aText);
                        double b = Double.parseDouble(bText);

                        // 2. PROCESAMIENTO: La lógica matemática se ejecuta aquí en el Servidor
                        Double resultado = ejecutarCalculo(operacion, a, b);

                        if (resultado == null) {
                            out.println(ProtocoloServer.serializarRespuesta("ERROR", "Operacion invalida o division por cero"));
                        } else {
                            // 3. SERIALIZACIÓN RESPUESTA: Empaquetamos el éxito ("OK|resultado")
                            out.println(ProtocoloServer.serializarRespuesta("OK", String.valueOf(resultado)));
                        }

                    } catch (NumberFormatException e) {
                        out.println(ProtocoloServer.serializarRespuesta("ERROR", "Los parametros no son numeros validos"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo encargado de realizar la operación aritmética de la calculadora
    private static Double ejecutarCalculo(String operation, double a, double b) {
        switch (operation.toUpperCase()) {
            case "SUMA": case "+": return a + b;
            case "RESTA": case "-": return a - b;
            case "MULT": case "*": return a * b;
            case "DIV": case "/":
                if (b == 0.0) return null; // Previene indeterminación
                return a / b;
            default: return null;
        }
    }
}
