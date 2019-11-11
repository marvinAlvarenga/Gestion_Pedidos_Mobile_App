package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SucursalesActivity extends AppCompatActivity {

    private TextView txtBuscar;
    private Button btnBuscar;
    private ListView listSucursales;
    private SucursalesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);

        this.txtBuscar = (TextView) findViewById(R.id.txt_sucur_buscar);
        this.btnBuscar = (Button) findViewById(R.id.btn_sucur_buscar);
        this.listSucursales = (ListView) findViewById(R.id.list_sucurs);

        this.adapter = new SucursalesListAdapter();
        this.listSucursales.setAdapter(adapter);
    }

    String[] names = {"Sucursal Numero 1", "Sucursal Numero 2", "Sucursal Numero 3", "Sucursal Numero 4", "Sucursal Numero 5", "Sucursal Numero 6"};
    String[] addreses = {"Direccion Numero 1", "Direccion Numero 2", "Direccion Numero 3", "Direccion Numero 4", "Direccion Numero 5", "Direccion Numero 6"};

    class SucursalesListAdapter extends BaseAdapter {

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_sucursales, null);
            TextView nombreSucursal = (TextView) view.findViewById(R.id.txt_sucur_nombre);
            TextView direccionSuccursal = (TextView) view.findViewById(R.id.txt_sucur_direccion);

            nombreSucursal.setText(names[i]);
            direccionSuccursal.setText(addreses[i]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetalleSucursal.class);
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
