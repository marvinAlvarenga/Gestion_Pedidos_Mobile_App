package com.igfgpo01.gestionpedidosmobile.requests;

public class ProductoRequest {

    private int cantidad;
    private int producto;

    public ProductoRequest(int cantidad, int producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getProducto() {
        return producto;
    }
}
