package com.igfgpo01.gestionpedidosmobile.singleton;

import com.igfgpo01.gestionpedidosmobile.models.Mensaje;
import com.igfgpo01.gestionpedidosmobile.models.Sucursal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public final class ChatSingleton extends Observable {

    //Instancia global del patrón Singleton
    private static ChatSingleton INSTANCE;

    public static ChatSingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ChatSingleton();
            INSTANCE.insertarDatosDePrueba();
        }

        return INSTANCE;
    }

    //constructor privado
    private ChatSingleton() { }

    //Todos los chat que posee con las diferentes sucursales
    private List<Sucursal> chats;

    //Añadir mensaje a un chat
    public void enviarMensaje(Sucursal sucursal, String mensaje){
        List<Mensaje> mensajes = sucursal.getMensajes();
        mensajes.add(new Mensaje(mensajes.size(), mensaje, true, new Date()));

        //Se han añadido cambios al observable y se notificaran
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public List<Sucursal> getChats() {
        return chats;
    }

    private void insertarDatosDePrueba() {
        if(chats == null){
            chats = new ArrayList<>();
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

}
