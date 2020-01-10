package com.igfgpo01.gestionpedidosmobile.singleton;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionLocalSingleton {

    public final String FILE_NAME = "data";
    public final String API_KEY = "apiKey";
    public final String ID_USER = "idUser";

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
        String key = getApiKey(context);
        return !key.isEmpty();
    }

    //Método encargado de devolver la APIKEY almacenada
    public String getApiKey(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(API_KEY, "");
    }

    //Método encargado de guardar una KEY especifica
    public void guardarKey(Context context, String newKey) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(API_KEY, newKey);
        editor.commit();
    }

    //Método encargado de devolver el id del usuario logueado
    public int getIdUserLoged(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getInt(ID_USER, 0);
    }

    //Método encargado de guardar el id del usuario logueado
    public void guardarIdUserLoged(Context context, int idUser) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID_USER, idUser);
        editor.commit();
    }
}
