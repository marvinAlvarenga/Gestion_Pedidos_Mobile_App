package com.igfgpo01.gestionpedidosmobile.singleton;

import android.content.Context;

import com.igfgpo01.gestionpedidosmobile.responses.ListadoSucursalesResponse;
import com.igfgpo01.gestionpedidosmobile.responses.ChatResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
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

    //Todas las sucursales disponibles con las que se puede interactural con chat y la aplicación
    private List<ListadoSucursalesResponse> todoasSucursales;

    //Todos los chat que posee con las diferentes sucursales
    private List<ListadoSucursalesResponse> bandejaEntrada;

    //Si los datos los chats del servidor han sido recuperados con éxito
    private boolean isMensajesSincronizados;

    //Encargado de recuperar la data de todas las sucursales disponibles
    public void recuperarSucursales(Context context) {
        if (todoasSucursales == null) {
            List<ListadoSucursalesResponse> data = null;

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<List<ListadoSucursalesResponse>> call = service.getAllSucursales();

            try {
                Response<List<ListadoSucursalesResponse>> response = call.execute();
                data = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }
            todoasSucursales = data;
        }
    }

    //Encargado de recuperar la data de la bandeja de entrada
    public void recuperarBandejaEntrada(Context context) {
        //Inicia recuperacion de las sucursales con chats
        List<ListadoSucursalesResponse> data = null;
        GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
        Call<List<ListadoSucursalesResponse>> call = service.getBandejaEntrada(SessionLocalSingleton.getInstance().getApiKey(context));
        try {
            Response<List<ListadoSucursalesResponse>> response = call.execute();
            data = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(data != null) {
            bandejaEntrada = new ArrayList<>();
            for (ListadoSucursalesResponse i : data) {
                bandejaEntrada.add(getSucursalById(i.getId()));
            }

            //Inicia la recuperación de los chats individuales
            //for (BandejaEntradaResponse sucur : bandejaEntrada) {
            //    recuperarMensajes(context, sucur);
            //}
        }

    }
    //recuperar los mensajes de una sucursal especifica
    public void recuperarMensajes(Context context, int idSucursal) {
        ListadoSucursalesResponse sucursal = getSucursalById(idSucursal);

        if (sucursal.getChats() == null || sucursal.getChats().size() == 0) {
            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<List<ChatResponse>> chats = service.getMensajes(idSucursal, SessionLocalSingleton.getInstance().getApiKey(context));
            List<ChatResponse> data = null;
            try {
                Response<List<ChatResponse>> response = chats.execute();
                data = response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (data != null) {
                sucursal.setChats(data);
            }
        }

    }

    //Recuperar la sucrusal por el id y sino devuelve null
    public ListadoSucursalesResponse getSucursalById(int idSucursal) {
        ListadoSucursalesResponse sucur = null;
        if (todoasSucursales != null)
            for (ListadoSucursalesResponse sucursal : todoasSucursales) {
                if (sucursal.getId() == idSucursal) {
                    sucur = sucursal;
                    break;
                }
            }
        return sucur;
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

    public List<ListadoSucursalesResponse> getTodoasSucursales() {
        return todoasSucursales;
    }

    public void setTodoasSucursales(List<ListadoSucursalesResponse> todoasSucursales) {
        this.todoasSucursales = todoasSucursales;
    }

    public List<ListadoSucursalesResponse> getBandejaDeEntrada() {
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
