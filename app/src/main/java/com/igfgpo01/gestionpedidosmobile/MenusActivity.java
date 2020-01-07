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

import com.igfgpo01.gestionpedidosmobile.responses.MenuResponse;
import com.igfgpo01.gestionpedidosmobile.responses.SucursalResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;
import com.igfgpo01.gestionpedidosmobile.singleton.MenusDeSucursalSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MenusActivity extends AppCompatActivity {

    private ListView listaMenus;
    private ProgressBar barMenus;
    private Button btnEnviarOrden;
    private MenusListAdapter adapter;

    private SucursalResponse sucursal; //la sucursal de la cual se están mostrando los menus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        this.listaMenus = (ListView) findViewById(R.id.list_menus);
        this.btnEnviarOrden = (Button) findViewById(R.id.btn_menu_enviar_orden);
        this.barMenus = (ProgressBar) findViewById(R.id.bar_menus);

        //Obtener parametros del Intent, los datos de la sucursal que se está mostrando
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) sucursal = (SucursalResponse) bundle.getSerializable(SucursalResponse.KEY);

        new MenusTask().execute();

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

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetalleSucursal.class);
                    intent.putExtra(SucursalResponse.KEY, sucursales.get(i));
                    startActivity(intent);
                }
            }); */
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
}
