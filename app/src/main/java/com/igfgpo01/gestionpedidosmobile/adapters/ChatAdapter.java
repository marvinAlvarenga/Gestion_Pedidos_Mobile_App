package com.igfgpo01.gestionpedidosmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igfgpo01.gestionpedidosmobile.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {
    private String[] listMensaje = {"¿Hola como estas?", "¿En que podemos ayudarle?"};
    private Context c;

    public ChatAdapter(Context c) {
        this.c = c;
    }

    //public void addMensaje(MensajeRecibir m){
    //    listMensaje.add(m);
    //    notifyItemInserted(listMensaje.size());
    //}

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_mensaje,parent,false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        holder.getNombre().setText("Marvin");
        holder.getMensaje().setText(listMensaje[position]);
        //if(listMensaje.get(position).getType_mensaje().equals("2")){
        //    holder.getFotoMensaje().setVisibility(View.VISIBLE);
          //  holder.getMensaje().setVisibility(View.VISIBLE);
            //Glide.with(c).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());
        //}else if(listMensaje.get(position).getType_mensaje().equals("1")){
          //  holder.getFotoMensaje().setVisibility(View.GONE);
           // holder.getMensaje().setVisibility(View.VISIBLE);
        //}
        //if(listMensaje.get(position).getFotoPerfil().isEmpty()){
            holder.getFotoMensajePerfil().setImageResource(R.mipmap.ic_launcher);
        //}else{
          //  Glide.with(c).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoMensajePerfil());
        //}
        //Long codigoHora = listMensaje.get(position).getHora();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");//a pm o am
        holder.getHora().setText(sdf.format(d));
    }

    @Override
    public int getItemCount() {
        return listMensaje.length;
    }
}
