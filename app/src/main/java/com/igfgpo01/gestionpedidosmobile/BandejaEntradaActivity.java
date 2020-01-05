package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.igfgpo01.gestionpedidosmobile.models.ConversacionSucursal;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;

public class BandejaEntradaActivity extends AppCompatActivity {

    private TextView txtBuscar;
    private Button btnBuscar;
    private ListView listMensajes;
    private BandejaEntradaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_entrada);

        this.txtBuscar = (TextView) findViewById(R.id.txt_bandeja_buscar);
        this.btnBuscar = (Button) findViewById(R.id.btn_bandeja_buscar);
        this.listMensajes = (ListView) findViewById(R.id.list_bandeja);

        this.adapter = new BandejaEntradaAdapter();
        this.listMensajes.setAdapter(adapter);
    }

    public static String KEY_CHAT_SELECCIONADO = "chatSeleccionado";

    class BandejaEntradaAdapter extends BaseAdapter {

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_bandeja, null);
            TextView nombreSucursal = (TextView) view.findViewById(R.id.txt_bandeja_nombre_sucursal);
            TextView ultimoMensaje = (TextView) view.findViewById(R.id.txt_bandeja_ultimo_mensaje);

            ConversacionSucursal conversacionSucursal = ChatSingleton.getInstance().getChats().get(i);
            int numTotalMensajes = conversacionSucursal.getMensajes().size();
            nombreSucursal.setText(conversacionSucursal.getNombreSucursal());
            ultimoMensaje.setText(conversacionSucursal.getMensajes().get(numTotalMensajes - 1).getContenido());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ChatActivity.class);
                    intent.putExtra(KEY_CHAT_SELECCIONADO, i);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return ChatSingleton.getInstance().getChats().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

    }
}
