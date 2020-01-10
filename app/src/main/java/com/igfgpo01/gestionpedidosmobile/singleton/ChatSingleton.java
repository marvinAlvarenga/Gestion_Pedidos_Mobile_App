package com.igfgpo01.gestionpedidosmobile.singleton;

import android.content.Context;

import com.igfgpo01.gestionpedidosmobile.models.ConversacionSucursal;
import com.igfgpo01.gestionpedidosmobile.models.Mensaje;
import com.igfgpo01.gestionpedidosmobile.responses.BandejaEntradaResponse;
import com.igfgpo01.gestionpedidosmobile.responses.ChatResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class ChatSingleton extends Observable {

    //Instancia global del patrón Singleton
    private static ChatSingleton INSTANCE;

    public static ChatSingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ChatSingleton();
            //INSTANCE.insertarDatosDePrueba();
        }

        return INSTANCE;
    }

    //constructor privado
    private ChatSingleton() { }

    //Todos los chat que posee con las diferentes sucursales
    private List<BandejaEntradaResponse> bandejaEntrada;

    //Si los datos los chats del servidor han sido recuperados con éxito
    private boolean isMensajesSincronizados;

    //Encargado de recuperar la data de la bandeja de entrada
    public void recuperarBandejaEntrada(Context context) {
        //Inicia recuperacion de las sucursales
        List<BandejaEntradaResponse> data = null;
        GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
        Call<List<BandejaEntradaResponse>> call = service.getBandejaEntrada(SessionLocalSingleton.getInstance().getApiKey(context));
        try {
            Response<List<BandejaEntradaResponse>> response = call.execute();
            data = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(data != null) {
            bandejaEntrada = data;

            //Inicia la recuperación de los chats individuales
            for (BandejaEntradaResponse sucur : bandejaEntrada) {
                recuperarMensajes(context, sucur);
            }
        }

    }
    //recuperar los mensajes de una sucursal especifica
    private void recuperarMensajes(Context context, final BandejaEntradaResponse sucursal) {
        GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
        Call<List<ChatResponse>> chats = service.getMensajes(sucursal.getId(), SessionLocalSingleton.getInstance().getApiKey(context));
        chats.enqueue(new Callback<List<ChatResponse>>() {
            @Override
            public void onResponse(Call<List<ChatResponse>> call, Response<List<ChatResponse>> response) {
                List<ChatResponse> mensajes = response.body();
                sucursal.setChats(mensajes);
            }

            @Override
            public void onFailure(Call<List<ChatResponse>> call, Throwable t) {

            }
        });
    }

    //Añadir mensaje a un chat
    /*public void enviarMensaje(ConversacionSucursal conversacionSucursal, String mensaje){
        List<Mensaje> mensajes = conversacionSucursal.getMensajes();
        mensajes.add(new Mensaje(mensajes.size(), mensaje, true, new Date()));

        //Se han añadido cambios al observable y se notificaran
        setChanged();
        notifyObservers();
        clearChanged();
    }*/

    public List<BandejaEntradaResponse> getBandejaDeEntrada() {
        return bandejaEntrada;
    }

    public boolean isMensajesSincronizados() {
        if (bandejaEntrada != null) isMensajesSincronizados = true;
        return isMensajesSincronizados;
    }


    public void setMensajesSincronizados(boolean mensajesSincronizados) {
        this.isMensajesSincronizados = mensajesSincronizados;
    }

    public static void setINSTANCE(ChatSingleton INSTANCE) {
        ChatSingleton.INSTANCE = INSTANCE;
    }
}
