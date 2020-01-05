package com.igfgpo01.gestionpedidosmobile.models;

import java.util.List;

public class ConversacionSucursal {

    private int idSucursal;
    private String nombreSucursal;
    private List<Mensaje> mensajes;

    public ConversacionSucursal(int idSucursal, String nombreSucursal, List<Mensaje> mensajes) {
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.mensajes = mensajes;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }
}
