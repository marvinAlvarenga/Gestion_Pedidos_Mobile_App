package com.igfgpo01.gestionpedidosmobile.responses;

import java.io.Serializable;

public class SucursalResponse implements Serializable {

    public static String KEY = "sucursal";

    private int id;
    private String nombre;
    private String direccion;

    public SucursalResponse(int id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }
}
