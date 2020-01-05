package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.igfgpo01.gestionpedidosmobile.adapters.ChatAdapter;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;

import java.util.Observable;
import java.util.Observer;

public class ChatActivity extends AppCompatActivity implements Observer {

    private RecyclerView rvMensajes;
    private ChatAdapter adapter;
    private TextView txtMensaje;
    private Button btnEnviar;

    private int chatMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Añadiendo el observer
        ChatSingleton.getInstance().addObserver(this);

        chatMostrar = getIntent().getIntExtra(BandejaEntradaActivity.KEY_CHAT_SELECCIONADO, -1);
        this.rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);

        this.adapter = new ChatAdapter(chatMostrar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.rvMensajes.setLayoutManager(linearLayoutManager);
        this.rvMensajes.setAdapter(adapter);

        //Lectura del contenido y asignacion de listener al boton para enviar el mensaje
        this.txtMensaje = (TextView) findViewById(R.id.txtMensaje);
        this.btnEnviar = (Button) findViewById(R.id.btnEnviar);

        this.btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = txtMensaje.getText().toString();
                if(texto != null && !texto.isEmpty()) {
                    //Enviar el mensaje
                    ChatSingleton.getInstance().enviarMensaje(adapter.getConversacionSucursalAMostrar(), texto);
                    txtMensaje.setText("");
                }
            }
        });
    }

    //Metodo del observer
    @Override
    public void update(Observable observable, Object o) {
        //Actualizar el recycled view
        adapter.notifyItemInserted(adapter.getMensajesDeLaSucursal().size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Eliminar observer cuando la actividad es destruida para no recibir notificaciones
        ChatSingleton.getInstance().deleteObserver(this);
    }
}
