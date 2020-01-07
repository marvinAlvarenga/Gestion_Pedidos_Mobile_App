package com.igfgpo01.gestionpedidosmobile.responses;

public class EnvioOrdenResponse {

    private double total;
    private int idPedido;

    public EnvioOrdenResponse(double total, int idPedido) {
        this.total = total;
        this.idPedido = idPedido;
    }

    public double getTotal() {
        return total;
    }

    public int getIdPedido() {
        return idPedido;
    }
}
