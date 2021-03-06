package com.igfgpo01.gestionpedidosmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.igfgpo01.gestionpedidosmobile.R;
import com.igfgpo01.gestionpedidosmobile.responses.ListadoSucursalesResponse;
import com.igfgpo01.gestionpedidosmobile.responses.ChatResponse;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {

    private List<ChatResponse> mensajesDeLaSucursal; //Todos los mensajes de la sucursal seleccionada
    private ListadoSucursalesResponse conversacionSucursalAMostrar; //La sucursar seleccionada
    private Context context;

    public ChatAdapter(int chatAMostrar, Context context) {
        this.context = context;
        if(chatAMostrar >= 0){
            this.conversacionSucursalAMostrar = ChatSingleton.getInstance().getSucursalById(chatAMostrar);
            this.mensajesDeLaSucursal = conversacionSucursalAMostrar.getChats();
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
        ChatResponse mensaje = mensajesDeLaSucursal.get(position);

        if(mensaje.getIdUserA().getId() == SessionLocalSingleton.getInstance().getIdUserLoged(context)) {
            holder.nombre.setText(R.string.lb_chat_yo);
            holder.fotoMensajePerfil.setImageResource(R.drawable.usuario);
        }
        else {
            holder.nombre.setText(conversacionSucursalAMostrar.getNombre());
            holder.fotoMensajePerfil.setImageResource(R.drawable.tiendaonline);
        }

        String contenido = mensaje.getContenido();
        try {
            JSONObject object = new JSONObject(contenido);
            holder.mensaje.setText(object.getString("mensaje"));
        } catch (JSONException e) {
            holder.mensaje.setText(mensaje.getContenido());
        }



        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        holder.hora.setText(sdf.format(mensaje.getFecha()));


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
        return mensajesDeLaSucursal != null ? mensajesDeLaSucursal.size() : 0;
    }

    public List<ChatResponse> getMensajesDeLaSucursal() {
        return mensajesDeLaSucursal;
    }

    public ListadoSucursalesResponse getConversacionSucursalAMostrar() {
        return conversacionSucursalAMostrar;
    }
}
