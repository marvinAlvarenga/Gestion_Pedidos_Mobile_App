package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.igfgpo01.gestionpedidosmobile.adapters.ChatAdapter;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvMensajes;
    private ChatAdapter adapter;

    private int chatMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatMostrar = getIntent().getIntExtra(BandejaEntradaActivity.KEY_CHAT_SELECCIONADO, -1);
        this.rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);

        this.adapter = new ChatAdapter(chatMostrar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.rvMensajes.setLayoutManager(linearLayoutManager);
        this.rvMensajes.setAdapter(adapter);
    }
}
