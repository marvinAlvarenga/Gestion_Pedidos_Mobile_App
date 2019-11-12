package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.igfgpo01.gestionpedidosmobile.adapters.ChatAdapter;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvMensajes;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);

        this.adapter = new ChatAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.rvMensajes.setLayoutManager(linearLayoutManager);
        this.rvMensajes.setAdapter(adapter);
    }
}
