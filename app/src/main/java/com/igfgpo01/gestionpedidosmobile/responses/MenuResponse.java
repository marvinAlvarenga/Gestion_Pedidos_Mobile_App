package com.igfgpo01.gestionpedidosmobile.responses;

import java.util.List;

public class MenuResponse {

    public static String MENU_SELECTED = "MENU_SELECCIONADO_KEY";

    private int id;
    private String nombre;
    private String descripcion;
    private List<ProductoResponse> productos;

    public MenuResponse(int id, String nombre, String descripcion, List<ProductoResponse> productos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = productos;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<ProductoResponse> getProductos() {
        return productos;
    }
}
