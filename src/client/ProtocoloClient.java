package client;

public class ProtocoloClient {
    /**
     * Convierte la operación y los parámetros a una trama de texto crudo.
     * Ejemplo: "SUMA", "10", "5" -> "SUMA|10|5"
     */
    public static String serializarPeticion(String operacion, String a, String b) {
        return operacion.trim().toUpperCase() + "|" + a.trim() + "|" + b.trim();
    }

    /**
     * Pica la respuesta del servidor para verificar el estado de la operación.
     * Ejemplo: "OK|15.0" -> ["OK", "15.0"]
     */
    public static String[] deserializarRespuesta(String respuestaCruda) {
        if (respuestaCruda == null || respuestaCruda.isEmpty()) {
            return new String[]{"ERROR", "Sin respuesta del servidor"};
        }
        // split("\\|") usa doble barra porque el pipe es un carácter especial en Regex
        return respuestaCruda.split("\\|");
    }
}
