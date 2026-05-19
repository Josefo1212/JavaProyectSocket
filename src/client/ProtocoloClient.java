package client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class ProtocoloClient {
    private static final Gson GSON = new Gson();

    /**
     * Convierte la operacion y los parametros a una trama JSON.
     * Ejemplo: "SUMA", "10", "5" -> {"operacion":"SUMA","datos":[10,5]}
     */
    public static String serializarPeticion(String operacion, String a, String b) {
        JsonArray datos = new JsonArray();
        datos.add(Double.parseDouble(a.trim()));
        datos.add(Double.parseDouble(b.trim()));
        PeticionDto peticion = new PeticionDto(operacion.trim().toUpperCase(), datos);
        return GSON.toJson(peticion);
    }

    /**
     * Convierte la respuesta JSON del servidor en un objeto RespuestaDto.
     */
    public static RespuestaDto deserializarRespuesta(String respuestaCruda) {
        if (respuestaCruda == null || respuestaCruda.isEmpty()) {
            return new RespuestaDto("ERROR", null, "Sin respuesta del servidor");
        }
        return GSON.fromJson(respuestaCruda, RespuestaDto.class);
    }
}
