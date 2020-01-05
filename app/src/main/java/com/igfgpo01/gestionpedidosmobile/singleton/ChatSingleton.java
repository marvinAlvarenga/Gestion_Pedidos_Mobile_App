package com.igfgpo01.gestionpedidosmobile.singleton;

import com.igfgpo01.gestionpedidosmobile.models.ConversacionSucursal;
import com.igfgpo01.gestionpedidosmobile.models.Mensaje;

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
    private List<ConversacionSucursal> chats;

    //Añadir mensaje a un chat
    public void enviarMensaje(ConversacionSucursal conversacionSucursal, String mensaje){
        List<Mensaje> mensajes = conversacionSucursal.getMensajes();
        mensajes.add(new Mensaje(mensajes.size(), mensaje, true, new Date()));

        //Se han añadido cambios al observable y se notificaran
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public List<ConversacionSucursal> getChats() {
        return chats;
    }

    private void insertarDatosDePrueba() {
        if(chats == null){
            chats = new ArrayList<>();
            ArrayList<Mensaje> mensajes1 = new ArrayList<>();
            mensajes1.add(new Mensaje(1, "Hola  buenos dias", true, new Date()));
            mensajes1.add(new Mensaje(2, "Hola  Marvin", false, new Date()));
            mensajes1.add(new Mensaje(3, "Tengo una duda", true, new Date()));

            ConversacionSucursal conversacionSucursal1 = new ConversacionSucursal(1, "Pollo Pollo", mensajes1);

            ArrayList<Mensaje> mensajes2 = new ArrayList<>();
            mensajes2.add(new Mensaje(1, "Hola  buenas noches", true, new Date()));
            mensajes2.add(new Mensaje(2, "Hola  Marvin", false, new Date()));
            ConversacionSucursal conversacionSucursal2 = new ConversacionSucursal(2, "Pupuseria El Rosario", mensajes2);

            chats.add(conversacionSucursal1);
            chats.add(conversacionSucursal2);
        }
    }

}
