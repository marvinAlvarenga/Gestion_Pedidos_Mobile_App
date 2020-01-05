package com.igfgpo01.gestionpedidosmobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.igfgpo01.gestionpedidosmobile.R;
import com.igfgpo01.gestionpedidosmobile.models.ConversacionSucursal;
import com.igfgpo01.gestionpedidosmobile.models.Mensaje;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {

    private List<Mensaje> mensajesDeLaSucursal; //Todos los mensajes de la sucursal seleccionada
    private ConversacionSucursal conversacionSucursalAMostrar; //La sucursar seleccionada

    public ChatAdapter(int chatAMostrar) {
        if(chatAMostrar >= 0){
            this.conversacionSucursalAMostrar = ChatSingleton.getInstance().getChats().get(chatAMostrar);
            this.mensajesDeLaSucursal = conversacionSucursalAMostrar.getMensajes();
        }
    }

    //public void addMensaje(MensajeRecibir m){
    //    listMensaje.add(m);
    //    notifyItemInserted(listMensaje.size());
    //}

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje,parent,false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        Mensaje mensaje = mensajesDeLaSucursal.get(position);

        if(mensaje.isEsMensajeLocal())  holder.nombre.setText("Usuario Local");
        else                            holder.nombre.setText(conversacionSucursalAMostrar.getNombreSucursal());

        holder.mensaje.setText(mensaje.getContenido());

        holder.fotoMensajePerfil.setImageResource(R.mipmap.ic_launcher);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        holder.hora.setText(sdf.format(mensaje.getFechaHora()));


        //holder.getNombre().setText("Marvin");
        //holder.getMensaje().setText(listMensaje[position]);
        //if(listMensaje.get(position).getType_mensaje().equals("2")){
        //    holder.getFotoMensaje().setVisibility(View.VISIBLE);
          //  holder.getMensaje().setVisibility(View.VISIBLE);
            //Glide.with(c).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());
        //}else if(listMensaje.get(position).getType_mensaje().equals("1")){
          //  holder.getFotoMensaje().setVisibility(View.GONE);
           // holder.getMensaje().setVisibility(View.VISIBLE);
        //}
        //if(listMensaje.get(position).getFotoPerfil().isEmpty()){
            //holder.getFotoMensajePerfil().setImageResource(R.mipmap.ic_launcher);
        //}else{
          //  Glide.with(c).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoMensajePerfil());
        //}
        //Long codigoHora = listMensaje.get(position).getHora();
        //Date d = new Date();
        //SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");//a pm o am
        //holder.getHora().setText(sdf.format(d));
    }

    @Override
    public int getItemCount() {
        return mensajesDeLaSucursal.size();
    }

    public List<Mensaje> getMensajesDeLaSucursal() {
        return mensajesDeLaSucursal;
    }

    public ConversacionSucursal getConversacionSucursalAMostrar() {
        return conversacionSucursalAMostrar;
    }
}
