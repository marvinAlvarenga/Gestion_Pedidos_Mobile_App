package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.igfgpo01.gestionpedidosmobile.responses.MenuResponse;
import com.igfgpo01.gestionpedidosmobile.responses.ProductoResponse;
import com.igfgpo01.gestionpedidosmobile.singleton.MenusDeSucursalSingleton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RealizarOrdenActivity extends AppCompatActivity {

    private ListView listaMenu;
    private TextView txtTotal;
    private MenuListAdapter adapter;

    private int indiceMenu; //Indice del menú a mostrar recibido como parámetro
    private final String URL_GENERAL_IMAGENES = "http://igf2020.southcentralus.cloudapp.azure.com/uploads/images/products/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_orden);

        Bundle bundle = getIntent().getExtras();
        indiceMenu = bundle.getInt(MenuResponse.MENU_SELECTED);

        listaMenu = (ListView) findViewById(R.id.list_productos);
        txtTotal = (TextView) findViewById(R.id.txt_re_total);

        adapter = new MenuListAdapter();
        listaMenu.setAdapter(adapter);

    }

    class MenuListAdapter extends BaseAdapter {

        private List<ProductoResponse> productos;

        public MenuListAdapter() {
            productos = MenusDeSucursalSingleton.getInstance().getMenus().get(indiceMenu).getProductos();
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_producto, null);
            ImageView imagen = (ImageView) view.findViewById(R.id.img_re_prod);
            TextView txtNombreProducto = (TextView) view.findViewById(R.id.txt_re_producto);
            TextView txtDescripProducto = (TextView) view.findViewById(R.id.txt_re_descripcion);
            final TextView txtCantidad = (TextView) view.findViewById(R.id.txt_re_cantidad);

            ImageButton btnSumar = (ImageButton) view.findViewById(R.id.btn_re_sumar);
            ImageButton btnRestar = (ImageButton) view.findViewById(R.id.btn_re_restar);


            final ProductoResponse producto = productos.get(i);

            String urlImagen = URL_GENERAL_IMAGENES + producto.getImage();
            Picasso.get().load(urlImagen).into(imagen);

            txtNombreProducto.setText(producto.getNombreProducto());
            txtDescripProducto.append(" " + producto.getDescripcion());
            txtCantidad.setText("" + producto.getCantidadSeleccionada());

            btnSumar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    producto.setCantidadSeleccionada(producto.getCantidadSeleccionada() + 1);
                    txtCantidad.setText("" + producto.getCantidadSeleccionada());
                }
            });

            btnRestar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(producto.getCantidadSeleccionada() > 0)
                        producto.setCantidadSeleccionada(producto.getCantidadSeleccionada() - 1);
                    txtCantidad.setText("" + producto.getCantidadSeleccionada());
                }
            });

            return view;
        }

        @Override
        public int getCount() {
            return productos.size();
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
