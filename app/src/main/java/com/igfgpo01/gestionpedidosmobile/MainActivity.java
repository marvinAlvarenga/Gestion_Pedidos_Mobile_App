package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

public class MainActivity extends AppCompatActivity {

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
        }


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
                Intent intent = new Intent(view.getContext(), OrdenesActivity.class);
                startActivity(intent);
            }
        });

        this.cardChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BandejaEntradaActivity.class);
                startActivity(intent);
            }
        });
    }
}
