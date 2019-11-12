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

    String[] names = {"Sucursal Numero 1", "Sucursal Numero 2", "Sucursal Numero 3", "Sucursal Numero 4", "Sucursal Numero 5", "Sucursal Numero 6"};
    String[] messaje = {"Como se encuentra", "Como se encuentra", "Como se encuentra", "Como se encuentra", "Como se encuentra", "Como se encuentra"};

    class BandejaEntradaAdapter extends BaseAdapter {

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_bandeja, null);
            TextView nombreSucursal = (TextView) view.findViewById(R.id.txt_bandeja_nombre_sucursal);
            TextView ultimoMensaje = (TextView) view.findViewById(R.id.txt_bandeja_ultimo_mensaje);

            nombreSucursal.setText(names[i]);
            ultimoMensaje.setText(messaje[i]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ChatActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return names.length;
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
