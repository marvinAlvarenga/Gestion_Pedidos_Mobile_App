package com.igfgpo01.gestionpedidosmobile.responses;

public class ProductoResponse {

    private String image;
    private int id;
    private String nombreProducto;
    private String descripcion;
    private String cantidad;
    private double precioVenta;
    private int cantidadSeleccionada; //no viene de la API, ayuda a la hora de crear la orden

    public ProductoResponse(String image, int id, String nombreProducto, String descripcion, String cantidad, double precioVenta, int cantidadSeleccionada) {
        this.image = image;
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.cantidadSeleccionada = cantidadSeleccionada;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public int getCantidadSeleccionada() {
        return cantidadSeleccionada;
    }

    public void setCantidadSeleccionada(int cantidadSeleccionada) {
        this.cantidadSeleccionada = cantidadSeleccionada;
    }
}
