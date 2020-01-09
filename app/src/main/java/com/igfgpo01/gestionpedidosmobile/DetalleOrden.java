package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igfgpo01.gestionpedidosmobile.responses.CancelarOrdenResponse;
import com.igfgpo01.gestionpedidosmobile.responses.OrdenDetalleResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;
import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DetalleOrden extends AppCompatActivity {

    private Button btnChat;
    private Button btnCancelar;
    private ProgressBar barDetalleOrden;
    private TextView txtFecha;
    private TextView txtTotal;
    private ListView listDetalleOrden;

    private DetalleOrdenListAdapter adapter;

    private int idOrden;
    private String estadoOrden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_orden);

        btnChat = (Button) findViewById(R.id.btn_de_or_chat);
        btnCancelar = (Button) findViewById(R.id.btn_de_or_cancelar);
        barDetalleOrden = (ProgressBar) findViewById(R.id.bar_deta_or);
        txtFecha = (TextView) findViewById(R.id.lb_deta_or_fecha);
        txtTotal = (TextView) findViewById(R.id.lb_deta_or_total);
        listDetalleOrden = (ListView) findViewById(R.id.list_deta_orden);

        Bundle bundle = getIntent().getExtras();
        idOrden = bundle.getInt(OrdenDetalleResponse.KEY_DETALLE_ORDEN_MOSTRAR);
        estadoOrden = bundle.getString(OrdenDetalleResponse.KEY_DETALLE_ORDEN_ESTADO);
        if(estadoOrden.equals(OrdenDetalleResponse.ESTADO_ORDEN_CANCELADO))
            btnCancelar.setVisibility(View.GONE);

        new DetalleOrdenTask().execute(idOrden);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetTest.isOnline(view.getContext()))
                    new CancelarOrdenTask().execute(idOrden);
                else Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Clase Adaptadora
    class DetalleOrdenListAdapter extends BaseAdapter {

        //Detalle de la Orden a mostrar
        private OrdenDetalleResponse detalleOrden;

        //Constructor del adaptador
        public DetalleOrdenListAdapter(OrdenDetalleResponse detalleOrden) {
            this.detalleOrden = detalleOrden;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_detalle_orden, null);

            TextView txtNumero = (TextView) view.findViewById(R.id.txt_deta_or_numero);
            TextView txtNombre = (TextView) view.findViewById(R.id.txt_deta_or_nombre);
            TextView txtCantidad = (TextView) view.findViewById(R.id.txt_deta_or_cantidad);
            TextView txtSubTotal = (TextView) view.findViewById(R.id.txt_deta_or_sub_total);

            txtNumero.setText(String.valueOf(i + 1));
            txtNombre.setText(detalleOrden.getProductos().get(i).getProducto().getNombreProducto());
            txtCantidad.setText("" + detalleOrden.getProductos().get(i).getCantidad());
            txtSubTotal.setText("$" + detalleOrden.getProductos().get(i).getSubTotal());

            return view;
        }

        @Override
        public int getCount() {
            return detalleOrden.getProductos().size();
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

    //Tarea asincrona de recuperar el detalle de la orden especificada
    private class DetalleOrdenTask extends AsyncTask<Integer, Void, OrdenDetalleResponse> {
        @Override
        protected void onPreExecute() {
            barDetalleOrden.setVisibility(View.VISIBLE);
        }

        @Override
        protected OrdenDetalleResponse doInBackground(Integer... integers) {
            int idOrdenRequest = integers[0];
            OrdenDetalleResponse data = null;

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<List<OrdenDetalleResponse>> call = service.getOrden(idOrdenRequest,
                    SessionLocalSingleton.getInstance().getApiKey(getApplicationContext()));

            try {
                Response<List<OrdenDetalleResponse>> response = call.execute();
                data = response.body().get(0); //Ya que viene como arreglo desde la API

            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(OrdenDetalleResponse ordenDetalleResponse) {
            if(ordenDetalleResponse != null) {
                adapter = new DetalleOrdenListAdapter(ordenDetalleResponse);
                listDetalleOrden.setAdapter(adapter);

                Date date = ordenDetalleResponse.getFecha();
                String formatDate = "hh:mm a dd/MMM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
                txtFecha.setText(sdf.format(date));
                txtTotal.setText("$" + ordenDetalleResponse.getTotal());
            } else Toast.makeText(getApplicationContext(), R.string.error_recuperacion_datos, Toast.LENGTH_SHORT).show();

            barDetalleOrden.setVisibility(View.GONE);
        }
    }

    //Tarea asincrona de cancelar una orden
    private class CancelarOrdenTask extends AsyncTask<Integer, Void, CancelarOrdenResponse> {
        @Override
        protected void onPreExecute() {
            barDetalleOrden.setVisibility(View.VISIBLE);
        }

        @Override
        protected CancelarOrdenResponse doInBackground(Integer... integers) {
            int idOrdenRequest = integers[0];
            CancelarOrdenResponse data = null;

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<CancelarOrdenResponse> call = service.cancelarOrden(idOrdenRequest,
                    SessionLocalSingleton.getInstance().getApiKey(getApplicationContext()));

            try {
                Response<CancelarOrdenResponse> response = call.execute();
                data = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(CancelarOrdenResponse cancelarOrdenResponse) {
            if(cancelarOrdenResponse != null && Boolean.parseBoolean(cancelarOrdenResponse.getEstado())) {
                Toast.makeText(getApplicationContext(), R.string.lb_deta_or_cancelada_exito, Toast.LENGTH_SHORT).show();

                getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } else Toast.makeText(getApplicationContext(), R.string.lb_deta_or_cancelada_fallo, Toast.LENGTH_LONG).show();

            barDetalleOrden.setVisibility(View.GONE);
        }
    }
}
