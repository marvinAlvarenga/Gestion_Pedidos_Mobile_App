package com.igfgpo01.gestionpedidosmobile.responses;

import java.util.List;

public class ListadoSucursalesResponse {

    private int id;
    private String nombre;
    private String direccion;
    private OrdenesResponse.Admin admin;

    public static final String KEY = "KEY_SUCURSAL_SELECCIONADA";


    //Campo adicional generado: Las conversaciones con una sucursal
    private List<ChatResponse> chats; //mensajes correspondientes con esta sucursal
    private boolean mensajesDescargados;

    public ListadoSucursalesResponse(int id, String nombre, String direccion, OrdenesResponse.Admin admin) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public boolean isMensajesDescargados() {
        return mensajesDescargados;
    }

    public void setMensajesDescargados(boolean mensajesDescargados) {
        this.mensajesDescargados = mensajesDescargados;
    }
}
