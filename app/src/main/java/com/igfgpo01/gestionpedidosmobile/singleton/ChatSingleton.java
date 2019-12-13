package com.igfgpo01.gestionpedidosmobile.singleton;

import com.igfgpo01.gestionpedidosmobile.models.Mensaje;
import com.igfgpo01.gestionpedidosmobile.models.Sucursal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class ChatSingleton {

    //constructor privado
    private ChatSingleton() { }

    //Instancia global de todos los chats que posee con las diferentes sucursales
    private static List<Sucursal> chats;

    public static List<Sucursal> getChats() {

        if(chats == null) {
            chats = new ArrayList<>();

            insertarDatosDePrueba();

        }

        return chats;
    }

    private static void insertarDatosDePrueba() {
        ArrayList<Mensaje> mensajes1 = new ArrayList<>();
        mensajes1.add(new Mensaje(1, "Hola  buenos dias", true, new Date()));
        mensajes1.add(new Mensaje(2, "Hola  Marvin", false, new Date()));
        mensajes1.add(new Mensaje(3, "Tengo una duda", true, new Date()));

        Sucursal sucursal1 = new Sucursal(1, "Pollo Pollo", mensajes1);

        ArrayList<Mensaje> mensajes2 = new ArrayList<>();
        mensajes2.add(new Mensaje(1, "Hola  buenas noches", true, new Date()));
        mensajes2.add(new Mensaje(2, "Hola  Marvin", false, new Date()));
        Sucursal sucursal2 = new Sucursal(2, "Pupuseria El Rosario", mensajes2);

        chats.add(sucursal1);
        chats.add(sucursal2);
    }

}
