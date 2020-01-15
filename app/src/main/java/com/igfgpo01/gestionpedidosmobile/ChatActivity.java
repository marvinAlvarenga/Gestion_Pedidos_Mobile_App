package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.igfgpo01.gestionpedidosmobile.adapters.ChatAdapter;
import com.igfgpo01.gestionpedidosmobile.responses.ChatResponse;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ChatActivity extends AppCompatActivity implements Observer {

    private RecyclerView rvMensajes;
    private ChatAdapter adapter;
    private TextView txtMensaje;
    private TextView txtNombre;
    private Button btnEnviar;
    private ProgressBar barChat;

    private int idSucursalchatMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.barChat = (ProgressBar) findViewById(R.id.bar_chat);

        //Añadiendo el observer
        ChatSingleton.getInstance().addObserver(this);

        idSucursalchatMostrar = getIntent().getIntExtra(BandejaEntradaActivity.KEY_CHAT_SELECCIONADO, -1);
        this.rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);


        //Lectura del contenido y asignacion de listener al boton para enviar el mensaje
        this.txtMensaje = (TextView) findViewById(R.id.txtMensaje);
        this.btnEnviar = (Button) findViewById(R.id.btnEnviar);

        //Agregando el nombre del la sucursal en la barra
        this.txtNombre = (TextView) findViewById(R.id.nombre);
        if (idSucursalchatMostrar != -1)
            this.txtNombre.setText(ChatSingleton.getInstance().getSucursalById(idSucursalchatMostrar).getNombre());

        new DescargarMensajesTask().execute(idSucursalchatMostrar);

        /*this.btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = txtMensaje.getText().toString();
                if(texto != null && !texto.isEmpty()) {
                    //Enviar el mensaje
                    ChatSingleton.getInstance().enviarMensaje(adapter.getConversacionSucursalAMostrar(), texto);
                    txtMensaje.setText("");
                }
            }
        }); */
    }

    //Encargada de descargar los mensajes en caso que no lo haya hecho antes
    private class DescargarMensajesTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            barChat.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Integer... integers) {

            ChatSingleton.getInstance().recuperarMensajes(getApplicationContext(), integers[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            barChat.setVisibility(View.GONE);

            adapter = new ChatAdapter(idSucursalchatMostrar, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvMensajes.setLayoutManager(linearLayoutManager);
            rvMensajes.setAdapter(adapter);
        }
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
