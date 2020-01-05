package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igfgpo01.gestionpedidosmobile.responses.SucursalResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SucursalesActivity extends AppCompatActivity {

    private TextView txtBuscar;
    private Button btnBuscar;
    private ListView listSucursales;
    private ProgressBar barSucursales;
    private SucursalesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);

        this.txtBuscar = (TextView) findViewById(R.id.txt_sucur_buscar);
        this.btnBuscar = (Button) findViewById(R.id.btn_sucur_buscar);
        this.listSucursales = (ListView) findViewById(R.id.list_sucurs);
        this.barSucursales = (ProgressBar) findViewById(R.id.bar_sucursales);

        new SucursalesTask().execute();

        //this.adapter = new SucursalesListAdapter();
        //this.listSucursales.setAdapter(adapter);
    }

    //String[] names = {"ConversacionSucursal Numero 1", "ConversacionSucursal Numero 2", "ConversacionSucursal Numero 3", "ConversacionSucursal Numero 4", "ConversacionSucursal Numero 5", "ConversacionSucursal Numero 6"};
    //String[] addreses = {"Direccion Numero 1", "Direccion Numero 2", "Direccion Numero 3", "Direccion Numero 4", "Direccion Numero 5", "Direccion Numero 6"};

    //Clase Adaptadora
    class SucursalesListAdapter extends BaseAdapter {

        //Listado de sucursales a mostrar
        private List<SucursalResponse> sucursales;

        //Constructor del adaptador
        public SucursalesListAdapter(List<SucursalResponse> sucursales) {
            this.sucursales = sucursales;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_sucursales, null);
            TextView nombreSucursal = (TextView) view.findViewById(R.id.txt_sucur_nombre);
            TextView direccionSuccursal = (TextView) view.findViewById(R.id.txt_sucur_direccion);

            nombreSucursal.setText(sucursales.get(i).getNombre());
            direccionSuccursal.setText(sucursales.get(i).getDireccion());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetalleSucursal.class);
                    intent.putExtra(SucursalResponse.KEY, sucursales.get(i));
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return sucursales.size();
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

    //Tarea asincrona de recuperar las sucursales
    private class SucursalesTask extends AsyncTask<Void, Void, List<SucursalResponse>> {

        @Override
        protected List<SucursalResponse> doInBackground(Void... voids) {
            List<SucursalResponse> data = null;

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<List<SucursalResponse>> call = service.getAllSucursales();

            try {
                Response<List<SucursalResponse>> response = call.execute();
                data = response.body();

                //Ordenar la colección en base a ID
                Collections.sort(data, new Comparator<SucursalResponse>() {
                    @Override
                    public int compare(SucursalResponse s1, SucursalResponse s2) {
                        return new Integer(s1.getId()).compareTo(new Integer(s2.getId()));
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(List<SucursalResponse> sucursalResponses) {
            if(sucursalResponses != null) {
                adapter = new SucursalesListAdapter(sucursalResponses);
                listSucursales.setAdapter(adapter);
            } else Toast.makeText(getApplicationContext(), R.string.error_recuperacion_datos, Toast.LENGTH_SHORT).show();

            barSucursales.setVisibility(View.GONE);
        }
    }
}
