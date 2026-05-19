package server;

import com.google.gson.JsonElement;

public class RespuestaDto {
    private String estado;
    private JsonElement resultado;
    private String mensaje;

    public RespuestaDto() {
    }

    public RespuestaDto(String estado, JsonElement resultado, String mensaje) {
        this.estado = estado;
        this.resultado = resultado;
        this.mensaje = mensaje;
    }

    public static RespuestaDto ok(JsonElement resultado) {
        return new RespuestaDto("OK", resultado, null);
    }

    public static RespuestaDto error(String mensaje) {
        return new RespuestaDto("ERROR", null, mensaje);
    }

    public String getEstado() {
        return estado;
    }

    public JsonElement getResultado() {
        return resultado;
    }

    public String getMensaje() {
        return mensaje;
    }
}

