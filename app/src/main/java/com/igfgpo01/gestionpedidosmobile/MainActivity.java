package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CardView cardPerfil;
    private CardView cardSucursales;
    private CardView cardPedidos;
    private CardView cardChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent intent = new Intent(view.getContext(), SucursalesActivity.class);
                startActivity(intent);
            }
        });

        this.cardPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Pedidos", Toast.LENGTH_SHORT).show();
            }
        });

        this.cardChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Chat", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
