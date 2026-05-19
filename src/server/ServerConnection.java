package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
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
    private final OperationExecutor executor;

    public ServerConnection(int port, OperationExecutor executor) {
        this.port = port;
        this.executor = executor;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port, 1, InetAddress.getByName(HOST))) {
            System.out.println("Servidor Java escuchando en el puerto " + port + "...");
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Llego JSON al servidor: " + line);

                    PeticionDto peticion = ProtocoloServer.deserializarPeticion(line);
                    RespuestaDto respuesta = procesarPeticion(peticion);
                    out.println(ProtocoloServer.serializarRespuesta(respuesta));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RespuestaDto procesarPeticion(PeticionDto peticion) {
        if (peticion == null || peticion.getOperacion() == null || peticion.getDatos() == null) {
            return RespuestaDto.error("Peticion invalida");
        }

        JsonElement datos = peticion.getDatos();
        if (!datos.isJsonArray()) {
            return RespuestaDto.error("Se esperan parametros en un arreglo JSON");
        }

        JsonArray array = datos.getAsJsonArray();
        if (array.size() < 2) {
            return RespuestaDto.error("Se requieren al menos dos parametros numericos");
        }

        try {
            double[] parametros = new double[array.size()];
            for (int i = 0; i < array.size(); i++) {
                parametros[i] = array.get(i).getAsDouble();
            }

            Double resultado = executor.ejecutar(peticion.getOperacion(), parametros);
            if (resultado == null) {
                return RespuestaDto.error("Operacion invalida o division por cero");
            }
            return RespuestaDto.ok(new JsonPrimitive(resultado));
        } catch (RuntimeException ex) {
            return RespuestaDto.error("Los parametros no son numeros validos");
        }
    }
}
