package com.igfgpo01.gestionpedidosmobile.responses;

import java.util.Date;

public class ChatResponse {

    private int id;
    private String contenido;
    private Usuario idUserA;
    private Usuario idUserB;
    private Date fecha;

    public ChatResponse(int id, String contenido, Usuario idUserA, Usuario idUserB, Date fecha) {
        this.id = id;
        this.contenido = contenido;
        this.idUserA = idUserA;
        this.idUserB = idUserB;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }

    public Usuario getIdUserA() {
        return idUserA;
    }

    public Usuario getIdUserB() {
        return idUserB;
    }

    public Date getFecha() {
        return fecha;
    }

    public class Usuario {
        private int id;
        private String nombres;
        private String usuario;

        public Usuario(int id, String nombres, String usuario) {
            this.id = id;
            this.nombres = nombres;
            this.usuario = usuario;
        }

        public int getId() {
            return id;
        }

        public String getNombres() {
            return nombres;
        }

        public String getUsuario() {
            return usuario;
        }
    }
}
