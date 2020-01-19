package com.igfgpo01.gestionpedidosmobile.singleton;

import android.content.Context;

public class UsuarioSingleton {

    private static UsuarioSingleton INSTANCE;
    private UsuarioSingleton() {}

    public static UsuarioSingleton getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UsuarioSingleton();
        return INSTANCE;
    }

    private int idUsuario;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void establecerId(Context context) {
        idUsuario = SessionLocalSingleton.getInstance().getIdUserLoged(context);
    }

}
