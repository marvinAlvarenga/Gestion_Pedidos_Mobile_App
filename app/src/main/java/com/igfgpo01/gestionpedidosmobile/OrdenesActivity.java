package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igfgpo01.gestionpedidosmobile.responses.ListadoSucursalesResponse;
import com.igfgpo01.gestionpedidosmobile.responses.OrdenDetalleResponse;
import com.igfgpo01.gestionpedidosmobile.responses.OrdenesResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class OrdenesActivity extends AppCompatActivity {

    private ListView listaOrdenes;
    private ProgressBar barOrdenes;

    private OrdenesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);

        listaOrdenes = (ListView) findViewById(R.id.list_ordenes);
        barOrdenes = (ProgressBar) findViewById(R.id.bar_ordenes);

        new OrdenesTask().execute();
    }


    class OrdenesListAdapter extends BaseAdapter {

        private List<OrdenesResponse> ordenes; //Ordenes a mostrar

        public OrdenesListAdapter(List<OrdenesResponse> ordenes) {
            this.ordenes = ordenes;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_ordenes, null);
            TextView nombreSucursal = (TextView) view.findViewById(R.id.txt_orden_sucursal);
            TextView estado = (TextView) view.findViewById(R.id.txt_orden_estado);
            TextView monto = (TextView) view.findViewById(R.id.txt_orden_monto);

            nombreSucursal.setText(ordenes.get(i).getSucursal().getNombre());
            estado.append(" " + ordenes.get(i).getEstado().getNombre());
            monto.setText("$" + ordenes.get(i).getTotal());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Mostrar el detalle de la orden seleccionada
                    if (InternetTest.isOnline(view.getContext())) {
                        Intent intent = new Intent(view.getContext(), DetalleOrden.class);
                        intent.putExtra(OrdenDetalleResponse.KEY_DETALLE_ORDEN_MOSTRAR, ordenes.get(i).getId());
                        intent.putExtra(OrdenDetalleResponse.KEY_DETALLE_ORDEN_ESTADO, ordenes.get(i).getEstado().getNombre());
                        intent.putExtra(ListadoSucursalesResponse.KEY, ordenes.get(i).getSucursal().getId());
                        startActivity(intent);
                    } else Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return ordenes.size();
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

    //Tarea asincrona de recuperar las sordenes realizadas por el usuario
    private class OrdenesTask extends AsyncTask<Void, Void, List<OrdenesResponse>> {

        @Override
        protected void onPreExecute() {
            barOrdenes.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<OrdenesResponse> doInBackground(Void... voids) {
            ChatSingleton.getInstance().recuperarSucursales(getApplicationContext());

            List<OrdenesResponse> data = null;

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<List<OrdenesResponse>> call = service.getAllOrdenes(SessionLocalSingleton.getInstance().getApiKey(getApplicationContext()));

            try {
                Response<List<OrdenesResponse>> response = call.execute();
                data = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(List<OrdenesResponse> ordenesResponses) {
            if(ordenesResponses != null) {
                if (ordenesResponses.size() > 0) {
                    adapter = new OrdenesListAdapter(ordenesResponses);
                    listaOrdenes.setAdapter(adapter);
                } else Toast.makeText(getApplicationContext(), R.string.txt_orden_sin_resultados, Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getApplicationContext(), R.string.error_recuperacion_datos, Toast.LENGTH_SHORT).show();

            barOrdenes.setVisibility(View.GONE);
        }
    }
}
