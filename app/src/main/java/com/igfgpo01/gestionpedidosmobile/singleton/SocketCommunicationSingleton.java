package com.igfgpo01.gestionpedidosmobile.singleton;

import android.os.Handler;
import android.util.Log;

import com.igfgpo01.gestionpedidosmobile.responses.ChatResponse;
import com.igfgpo01.gestionpedidosmobile.responses.ListadoSucursalesResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class SocketCommunicationSingleton {

    private static SocketCommunicationSingleton INSTANCE;
    private static final String URL_SOCKET = "ws://igf2020.southcentralus.cloudapp.azure.com:8080";

    private SocketCommunicationSingleton(){}

    public static SocketCommunicationSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SocketCommunicationSingleton();
        }
        if (!INSTANCE.isStarted) INSTANCE.startSocket();

        return INSTANCE;
    }

    public static void setINSTANCE(SocketCommunicationSingleton INSTANCE) {
        SocketCommunicationSingleton.INSTANCE = INSTANCE;
    }

    private OkHttpClient client;
    private MyWebSocketListener listener;
    private WebSocket webSocket;
    private boolean isStarted;

    public WebSocket getWebSocket() {
        return webSocket;
    }

    private void startSocket() {
        client = new OkHttpClient();
        Request request = new Request.Builder().url(URL_SOCKET).build();
        listener = new MyWebSocketListener();
        webSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
        isStarted = true;
    }

    //Clase listener para el socket
    private final class MyWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            String cad = "{\"userData\":\"" + UsuarioSingleton.getInstance().getIdUsuario() +
                    "\",\"tipo\":\"1\",\"destino\":\"\",\"mensaje\":\"\",\"token\":\"something\"}";
            Log.d("fromSocket", "onOpen: " + cad);
            webSocket.send(cad);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.d("fromSocket", "onMessage: " + text);

            try {
                JSONObject jsonObject = new JSONObject(text);
                if (jsonObject.getString("tipo").equals("2")) { //MENSAJE RECIVIDO ES TIPO 2
                    Log.d("fromSocket", "TIPO 2");
                    final ListadoSucursalesResponse sucursal = ChatSingleton.getInstance().
                            getSucursalById(Integer.parseInt(jsonObject.getString("sucursal")));

                    boolean isNullAlInicio = sucursal.getChats() == null;

                    final List<ChatResponse> mensajes = isNullAlInicio
                            ? new ArrayList<ChatResponse>()
                            : sucursal.getChats();
                    if (isNullAlInicio)
                        sucursal.setChats(mensajes);


                    ChatResponse.Usuario userA = new ChatResponse.Usuario(Integer.parseInt(jsonObject.getString("origen")), null, null);
                    ChatResponse.Usuario userB = new ChatResponse.Usuario(UsuarioSingleton.getInstance().getIdUsuario(), null, null);

                    final ChatResponse mensajeRecivido = new ChatResponse(mensajes.size(),
                            jsonObject.getString("mensaje"), userA, userB, new Date());


                    HandlerSingleton.getInstance().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            ChatSingleton.getInstance().anadirMensajeAChat(mensajes, mensajeRecivido, sucursal);
                            Log.d("fromSocket", "HANFLER");
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            isStarted = false;
            Log.d("fromSocket", "onClosing: " + code + " REASON: " +reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            isStarted = false;
            Log.d("fromSocket", "onClosed: " + code + " REASON: " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            isStarted = false;
            Log.d("fromSocket", "onFailure: " + t.getMessage() + " RESPONSE: " + response);
        }
    }
}
