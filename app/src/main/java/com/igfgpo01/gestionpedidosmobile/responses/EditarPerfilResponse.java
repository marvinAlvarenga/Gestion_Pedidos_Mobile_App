package com.igfgpo01.gestionpedidosmobile.responses;

public class EditarPerfilResponse {

    private String estado;
    private String error;

    public EditarPerfilResponse(String estado, String error) {
        this.estado = estado;
        this.error = error;
    }

    public String getEstado() {
        return estado;
    }

    public String getError() {
        return error;
    }
}
