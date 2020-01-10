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

import com.igfgpo01.gestionpedidosmobile.responses.BandejaEntradaResponse;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BandejaEntradaActivity extends AppCompatActivity {

    private TextView txtBuscar;
    private Button btnBuscar;
    private ListView listMensajes;
    private ProgressBar barBandejaEntrada;
    private BandejaEntradaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_entrada);

        this.txtBuscar = (TextView) findViewById(R.id.txt_bandeja_buscar);
        this.btnBuscar = (Button) findViewById(R.id.btn_bandeja_buscar);
        this.listMensajes = (ListView) findViewById(R.id.list_bandeja);
        this.barBandejaEntrada = (ProgressBar) findViewById(R.id.bar_bandeja_entrada);

        new BandejaDeEntradaTask().execute();

    }

    public static String KEY_CHAT_SELECCIONADO = "chatSeleccionado";

    class BandejaEntradaAdapter extends BaseAdapter {

        private List<BandejaEntradaResponse> bandejaEntrada;

        public BandejaEntradaAdapter(List<BandejaEntradaResponse> bandejaEntrada) {
            this.bandejaEntrada = bandejaEntrada;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_bandeja, null);
            TextView txtNombreSucursal = (TextView) view.findViewById(R.id.txt_bandeja_nombre_sucursal);

            txtNombreSucursal.setText(bandejaEntrada.get(i).getNombre());

            /*ConversacionSucursal conversacionSucursal = ChatSingleton.getInstance().getChats().get(i);
            int numTotalMensajes = conversacionSucursal.getMensajes().size();
            nombreSucursal.setText(conversacionSucursal.getNombreSucursal()); Observer Local*/

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
            return ChatSingleton.getInstance().getBandejaDeEntrada().size();
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

    //Tarea asincrona de recuperar la bandeja de entrada
    private class BandejaDeEntradaTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            barBandejaEntrada.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ChatSingleton chatSingleton = ChatSingleton.getInstance();
            if (!chatSingleton.isMensajesSincronizados()) chatSingleton.recuperarBandejaEntrada(getApplicationContext());

            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            List<BandejaEntradaResponse> bandejaEntradaResponses = ChatSingleton.getInstance().getBandejaDeEntrada();
            if(bandejaEntradaResponses != null) {
                adapter = new BandejaEntradaAdapter(bandejaEntradaResponses);
                listMensajes.setAdapter(adapter);
            } else Toast.makeText(getApplicationContext(), R.string.error_recuperacion_datos, Toast.LENGTH_SHORT).show();

            barBandejaEntrada.setVisibility(View.GONE);
        }
    }
}
