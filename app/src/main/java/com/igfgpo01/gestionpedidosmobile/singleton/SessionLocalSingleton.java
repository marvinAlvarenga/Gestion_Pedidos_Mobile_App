package com.igfgpo01.gestionpedidosmobile.singleton;

import android.content.Context;

public class SessionLocalSingleton {

    public final String FILE_NAME = "data";
    public final String API_KEY = "apiKey";

    private static SessionLocalSingleton sessionLocalSingleton;

    private SessionLocalSingleton() {}

    public static SessionLocalSingleton getInstance() {
        if (sessionLocalSingleton == null) {

            sessionLocalSingleton =  new SessionLocalSingleton();

        }

        return sessionLocalSingleton;
    }

    //Metodo encargado de verificar que el usuario tiene una sesion activa válida en LOCAL
    public boolean hasValidKeyInLocal(Context context) {
        String key = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(API_KEY, "");
        return !key.isEmpty();
    }
}
