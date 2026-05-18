package server;

public class ProtocoloServer {
    /**
     * Traduce los bytes de la red a variables procesables por el servidor.
     * Ejemplo: "SUMA|10|5" -> ["SUMA", "10", "5"]
     */
    public static String[] deserializarPeticion(String peticionCruda) {
        if (peticionCruda == null || peticionCruda.isEmpty()) {
            return null;
        }
        return peticionCruda.split("\\|");
    }

    /**
     * Convierte el resultado del cálculo en la trama de respuesta reglamentaria.
     * Ejemplo: "OK", "25.5" -> "OK|25.5"
     */
    public static String serializarRespuesta(String estado, String contenido) {
        return estado.toUpperCase() + "|" + contenido;
    }
}
