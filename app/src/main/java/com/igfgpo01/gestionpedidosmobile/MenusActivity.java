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

import com.igfgpo01.gestionpedidosmobile.requests.NuevaOrdenRequest;
import com.igfgpo01.gestionpedidosmobile.responses.EnvioOrdenResponse;
import com.igfgpo01.gestionpedidosmobile.responses.ListadoSucursalesResponse;
import com.igfgpo01.gestionpedidosmobile.responses.MenuResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.MenusDeSucursalSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MenusActivity extends AppCompatActivity {

    private ListView listaMenus;
    private ProgressBar barMenus;
    private Button btnEnviarOrden;
    private TextView txtTotalOrden;
    private MenusListAdapter adapter;

    private ListadoSucursalesResponse sucursal; //la sucursal de la cual se están mostrando los menus
    private boolean primeraVez = true; //Bandera para saber si la activiy esta recien creada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        this.listaMenus = (ListView) findViewById(R.id.list_menus);
        this.btnEnviarOrden = (Button) findViewById(R.id.btn_menu_enviar_orden);
        this.barMenus = (ProgressBar) findViewById(R.id.bar_menus);
        this.txtTotalOrden = (TextView) findViewById(R.id.lb_menu_total);

        //Obtener parametros del Intent, los datos de la sucursal que se está mostrando
        int idSucursal = getIntent().getIntExtra(ListadoSucursalesResponse.KEY, -1);
        if(idSucursal > 0 ) sucursal = ChatSingleton.getInstance().getSucursalById(idSucursal);

        new MenusTask().execute();

        btnEnviarOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!InternetTest.isOnline(view.getContext()))
                    Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
                else {
                    view.setEnabled(false);
                    NuevaOrdenRequest nuevaOrdenRequest = new NuevaOrdenRequest(sucursal.getId());
                    if (!nuevaOrdenRequest.isNuevaOrdenLista()){
                        Toast.makeText(view.getContext(), R.string.lb_re_orden_incompleta, Toast.LENGTH_LONG).show();
                        view.setEnabled(true);
                    } else new EnviarOrdenTask().execute(nuevaOrdenRequest);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!primeraVez) {
            txtTotalOrden.setText("$" + MenusDeSucursalSingleton.getInstance().getTotalMenus());
        }else primeraVez = false;
    }

    //Clase Adaptadora
    class MenusListAdapter extends BaseAdapter {

        //Listado de menus a mostrar
        private List<MenuResponse> menus;

        //Constructor del adaptador
        public MenusListAdapter() {
            this.menus = MenusDeSucursalSingleton.getInstance().getMenus();
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_menus_disponibles, null);
            TextView nombreMenu = (TextView) view.findViewById(R.id.txt_menus_nombre);
            TextView descripcionMenu = (TextView) view.findViewById(R.id.txt_menus_descripcion);

            nombreMenu.setText(menus.get(i).getNombre());
            descripcionMenu.setText(menus.get(i).getDescripcion());

            //Cargar la Activity del detalle de los menus.

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RealizarOrdenActivity.class);
                    intent.putExtra(MenuResponse.MENU_SELECTED, i);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return menus.size();
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

    //Tarea asincrona de recuperar los menus disponibles
    private class MenusTask extends AsyncTask<Void, Void, List<MenuResponse>> {
        @Override
        protected void onPreExecute() {
            barMenus.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MenuResponse> doInBackground(Void... voids) {
            List<MenuResponse> data = null;

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<List<MenuResponse>> call = service.getMenusDeSucursal(sucursal.getId(), SessionLocalSingleton.getInstance().getApiKey(getApplicationContext()));

            try {
                Response<List<MenuResponse>> response = call.execute();
                data = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(List<MenuResponse> menuResponses) {
            if(menuResponses != null) {
                MenusDeSucursalSingleton.getInstance().setMenus(menuResponses); //Setear en el singleton a compartir
                adapter = new MenusListAdapter();
                listaMenus.setAdapter(adapter);
            } else Toast.makeText(getApplicationContext(), R.string.error_recuperacion_datos, Toast.LENGTH_SHORT).show();

            barMenus.setVisibility(View.GONE);
        }
    }

    //Tarea asincrona que se encarga de enviar la nueva orden
    private class EnviarOrdenTask extends AsyncTask<NuevaOrdenRequest, Void, EnvioOrdenResponse> {
        @Override
        protected void onPreExecute() {
            barMenus.setVisibility(View.VISIBLE);
        }

        @Override
        protected EnvioOrdenResponse doInBackground(NuevaOrdenRequest... ordenes) {
            NuevaOrdenRequest ordenRequest = ordenes[0];
            EnvioOrdenResponse dataResponse = null;

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<EnvioOrdenResponse> call = service.enviarOrdenNueva(
                            SessionLocalSingleton.getInstance().getApiKey(getApplicationContext()),
                            ordenRequest);

            try {
                Response<EnvioOrdenResponse> response = call.execute();
                dataResponse = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return dataResponse;
        }

        @Override
        protected void onPostExecute(EnvioOrdenResponse envioOrdenResponse) {
            barMenus.setVisibility(View.GONE);
            btnEnviarOrden.setEnabled(true);
            if(envioOrdenResponse != null) {
                Toast.makeText(getApplicationContext(), R.string.lb_re_orden_exito, Toast.LENGTH_LONG).show();

                getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            } else Toast.makeText(getApplicationContext(), R.string.lb_re_orden_fallo, Toast.LENGTH_LONG).show();
        }
    }
}
