package client;

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

