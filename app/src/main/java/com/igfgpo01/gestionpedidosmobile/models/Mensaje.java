package com.igfgpo01.gestionpedidosmobile.models;

import java.util.Date;

public class Mensaje {

    private int idMensaje;
    private String contenido;
    private boolean esMensajeLocal;
    private Date fechaHora;

    public Mensaje(int idMensaje, String contenido, boolean esMensajeLocal, Date fechaHora) {
        this.idMensaje = idMensaje;
        this.contenido = contenido;
        this.esMensajeLocal = esMensajeLocal;
        this.fechaHora = fechaHora;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public boolean isEsMensajeLocal() {
        return esMensajeLocal;
    }

    public Date getFechaHora() {
        return fechaHora;
    }
}
