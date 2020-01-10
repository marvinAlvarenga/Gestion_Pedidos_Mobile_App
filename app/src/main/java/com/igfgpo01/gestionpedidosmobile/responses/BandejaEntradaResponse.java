package com.igfgpo01.gestionpedidosmobile.responses;

import java.util.List;

public class BandejaEntradaResponse {

    private int id;
    private String nombre;
    private OrdenesResponse.Admin admin;


    //Campo adicional generado: Las conversaciones con una sucursal
    private List<ChatResponse> chats; //mensajes correspondientes con esta sucursal

    public BandejaEntradaResponse(int id, String nombre, OrdenesResponse.Admin admin) {
        this.id = id;
        this.nombre = nombre;
        this.admin = admin;
    }

    public List<ChatResponse> getChats() {
        return chats;
    }

    public void setChats(List<ChatResponse> chats) {
        this.chats = chats;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public OrdenesResponse.Admin getAdmin() {
        return admin;
    }
}
