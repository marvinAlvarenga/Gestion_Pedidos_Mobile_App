package com.igfgpo01.gestionpedidosmobile.responses;

import java.util.Date;
import java.util.List;

public class OrdenDetalleResponse {

    public static final String KEY_DETALLE_ORDEN_MOSTRAR = "KEY_DETALLE_ORDEN_MOSTRAR";

    private Date fecha;
    private double total;
    private List<Productos> productos;

    public OrdenDetalleResponse(Date fecha, double total, List<Productos> productos) {
        this.fecha = fecha;
        this.total = total;
        this.productos = productos;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public List<Productos> getProductos() {
        return productos;
    }

    public class Productos {
        private int cantidad;
        private double subTotal;
        private Producto producto;

        public Productos(int cantidad, double subTotal, Producto producto) {
            this.cantidad = cantidad;
            this.subTotal = subTotal;
            this.producto = producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public double getSubTotal() {
            return subTotal;
        }

        public Producto getProducto() {
            return producto;
        }
    }

    public class Producto {
        private String nombreProducto;

        public Producto(String nombreProducto) {
            this.nombreProducto = nombreProducto;
        }

        public String getNombreProducto() {
            return nombreProducto;
        }
    }

}
