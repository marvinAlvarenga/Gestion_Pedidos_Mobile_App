package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OrdenesActivity extends AppCompatActivity {

    private ListView listaOrdenes;

    private OrdenesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);

        listaOrdenes = (ListView) findViewById(R.id.list_ordenes);

        adapter = new OrdenesListAdapter();
        listaOrdenes.setAdapter(adapter);
    }

    String[] sucursales = {"Sucursal Numero 1", "Sucursal Numero 2", "Sucursal Numero 3", "Sucursal Numero 4", "Sucursal Numero 5", "Sucursal Numero 6"};
    String[] estados = {"Estado: en proceso", "Estado: en proceso", "Estado: en proceso", "Estado: en proceso", "Estado: en proceso", "Estado: en proceso"};
    String[] montos = {"$9.99", "$9.99", "$9.99", "$9.99", "$9.99", "$9.99"};

    class OrdenesListAdapter extends BaseAdapter {

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_ordenes, null);
            TextView nombreSucursal = (TextView) view.findViewById(R.id.txt_orden_sucursal);
            TextView estado = (TextView) view.findViewById(R.id.txt_orden_estado);
            TextView monto = (TextView) view.findViewById(R.id.txt_orden_monto);

            nombreSucursal.setText(sucursales[i]);
            estado.setText(estados[i]);
            monto.setText(montos[i]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetalleOrden.class);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return sucursales.length;
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
