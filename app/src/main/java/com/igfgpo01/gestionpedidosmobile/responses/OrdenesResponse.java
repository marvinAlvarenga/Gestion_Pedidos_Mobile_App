package com.igfgpo01.gestionpedidosmobile.responses;

import java.util.Date;

public class OrdenesResponse {

    private int id;
    private Date fecha;
    private double total;
    private Sucursal sucursal;
    private Estado estado;

    public OrdenesResponse(int id, Date fecha, double total, Sucursal sucursal, Estado estado) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.sucursal = sucursal;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public Estado getEstado() {
        return estado;
    }

    public class Sucursal {
        private int id;
        private String nombre;
        private Admin admin;
        public Sucursal(int id, String nombre, Admin admin){
            this.id = id; this.nombre = nombre; this.admin = admin;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public Admin getAdmin() {
            return admin;
        }
    }

    public class Admin {
        private Usuario usuario;
        public Admin(Usuario usuario){ this.usuario = usuario; }

        public Usuario getUsuario() {
            return usuario;
        }
    }

    public class Usuario {
        private int id;
        public Usuario(int id) { this.id = id; }

        public int getId() {
            return id;
        }
    }

    public class Estado {
        private String nombre;
        public Estado(String nombre) { this.nombre = nombre; }

        public String getNombre() {
            return nombre;
        }
    }

}
