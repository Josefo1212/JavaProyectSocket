package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ProtocoloServer {
    private static final Gson GSON = new Gson();

    /**
     * Traduce el JSON recibido en una peticion estructurada.
     */
    public static PeticionDto deserializarPeticion(String peticionCruda) {
        if (peticionCruda == null || peticionCruda.isEmpty()) {
            return null;
        }
        try {
            return GSON.fromJson(peticionCruda, PeticionDto.class);
        } catch (JsonSyntaxException ex) {
            return null;
        }
    }

    /**
     * Convierte la respuesta en JSON para enviar al cliente.
     */
    public static String serializarRespuesta(RespuestaDto respuesta) {
        return GSON.toJson(respuesta);
    }
}
