package com.igfgpo01.gestionpedidosmobile.responses;

public class VerPerfilResponse {

    private String email;
    private String username;
    private String direccion;

    public VerPerfilResponse(String email, String username, String direccion) {
        this.email = email;
        this.username = username;
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getDireccion() {
        return direccion;
    }
}
