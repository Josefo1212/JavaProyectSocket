package client;

import com.google.gson.JsonElement;

public class PeticionDto {
    private String operacion;
    private JsonElement datos;

    public PeticionDto() {
    }

    public PeticionDto(String operacion, JsonElement datos) {
        this.operacion = operacion;
        this.datos = datos;
    }

    public String getOperacion() {
        return operacion;
    }

    public JsonElement getDatos() {
        return datos;
    }
}

