package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.igfgpo01.gestionpedidosmobile.responses.ChatResponse;
import com.igfgpo01.gestionpedidosmobile.responses.ListadoSucursalesResponse;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.HandlerSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.SocketCommunicationSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.UsuarioSingleton;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private CardView cardPerfil;
    private CardView cardSucursales;
    private CardView cardPedidos;
    private CardView cardChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Verificar si hay llave almacenada localmente
        if(!SessionLocalSingleton.getInstance().hasValidKeyInLocal(this)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        //Veriricar que la aplicacion tiene conexion a internet
        if(!InternetTest.isOnline(this)){
            Toast.makeText(this, R.string.sin_internet, Toast.LENGTH_SHORT).show();
        } else {
            //Tratar de descargar las sucursales
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    ChatSingleton.getInstance().recuperarSucursales(getApplicationContext());
                    return null;
                }
            }.execute();

            //Tratar de establecer una conexion socket
            SocketCommunicationSingleton.getInstance();
        }
        //Añadiendo el observer
        ChatSingleton.getInstance().addObserver(this);

        //Añadir al scope global el usuario logueado
        UsuarioSingleton user = UsuarioSingleton.getInstance();
        user.establecerId(getApplicationContext());

        //Crear un handler que haga referencia al hilo principal
        HandlerSingleton.getInstance();


        setContentView(R.layout.activity_main);

        this.cardPerfil = (CardView) findViewById(R.id.card_perfil);
        this.cardSucursales = (CardView) findViewById(R.id.card_sucursales);
        this.cardPedidos = (CardView) findViewById(R.id.card_mis_pedidos);
        this.cardChats = (CardView) findViewById(R.id.card_chat);

        this.cardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MiPerfilActivity.class);
                startActivity(intent);
            }
        });

        this.cardSucursales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetTest.isOnline(view.getContext())){
                    Intent intent = new Intent(view.getContext(), SucursalesActivity.class);
                    startActivity(intent);
                } else Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
            }
        });

        this.cardPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetTest.isOnline(view.getContext())) {
                    Intent intent = new Intent(view.getContext(), OrdenesActivity.class);
                    startActivity(intent);
                } else Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
            }
        });

        this.cardChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetTest.isOnline(view.getContext())) {
                    Intent intent = new Intent(view.getContext(), BandejaEntradaActivity.class);
                    startActivity(intent);
                } else Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        ListadoSucursalesResponse sucursal = (ListadoSucursalesResponse) o;
        int longi = sucursal.getChats().size();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.tiendaonline)
                .setContentTitle("Nuevo mensaje de: " + sucursal.getNombre())
                .setLights(Color.MAGENTA, 1000, 1000)
                .setVibrate(new long[]{1000L, 1000L})
                .setTicker("Nuevo Mensaje")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setAutoCancel(true);

        if (longi > 0) {
            ChatResponse lastMenssage = sucursal.getChats().get(longi - 1);
            builder.setContentText(lastMenssage.getContenido());
            if (lastMenssage.getIdUserA().getId() == UsuarioSingleton.getInstance().getIdUsuario()) {
                Toast.makeText(getApplicationContext(), "Mensaje enviado...", Toast.LENGTH_SHORT).show();
            }

        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(BandejaEntradaActivity.KEY_CHAT_SELECCIONADO, sucursal.getId());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());

        Log.d("fromSocket", "UPDATE");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Eliminar observer cuando la actividad es destruida para no recibir notificaciones
        ChatSingleton.getInstance().deleteObserver(this);
    }
}
