package client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.util.List;

public class ProtocoloClient {
    private static final Gson GSON = new Gson();

    /**
     * Convierte la operacion y los parametros a una trama JSON.
     * Ejemplo: "SUMA", ["10", "5", "2"] -> {"operacion":"SUMA","datos":[10,5,2]}
     */
    public static String serializarPeticion(String operacion, List<String> valores) {
        JsonArray datos = new JsonArray();
        for (String valor : valores) {
            datos.add(Double.parseDouble(valor.trim()));
        }
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
