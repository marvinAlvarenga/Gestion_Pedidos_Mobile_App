package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RealizarOrdenActivity extends AppCompatActivity {

    private ListView listaMenu;
    private TextView txtTotal;
    private Button btnEnviar;
    private Button btnCancelar;
    private MenuListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_orden);

        listaMenu = (ListView) findViewById(R.id.list_menu);
        txtTotal = (TextView) findViewById(R.id.txt_re_total);
        btnEnviar = (Button) findViewById(R.id.btn_re_enviar);
        btnCancelar = (Button) findViewById(R.id.btn_re_cancelar);

        adapter = new MenuListAdapter();
        listaMenu.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OrdenesActivity.class);
                startActivity(intent);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    int[] images = {R.drawable.hamburguesa, R.drawable.zanahoria};
    String[] productos = {"Hamburguesa", "Zanahoria"};
    int[] cantidad = {3, 5};

    class MenuListAdapter extends BaseAdapter {

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list_menu, null);
            ImageView imagen = (ImageView) view.findViewById(R.id.img_re_prod);
            TextView txtNombreProducto = (TextView) view.findViewById(R.id.txt_re_producto);
            final TextView txtCantidad = (TextView) view.findViewById(R.id.txt_re_cantidad);

            ImageButton btnSumar = (ImageButton) view.findViewById(R.id.btn_re_sumar);
            ImageButton btnRestar = (ImageButton) view.findViewById(R.id.btn_re_restar);

            imagen.setImageResource(images[i]);
            txtNombreProducto.setText(productos[i]);
            txtCantidad.setText("" + cantidad[i]);

            btnSumar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int cantidad = Integer.parseInt(txtCantidad.getText().toString());
                    txtCantidad.setText(String.valueOf(++cantidad));
                }
            });

            btnRestar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int cantidad = Integer.parseInt(txtCantidad.getText().toString());
                    cantidad = cantidad > 0 ? cantidad - 1 : cantidad;
                    txtCantidad.setText(String.valueOf(cantidad));
                }
            });

            return view;
        }

        @Override
        public int getCount() {
            return images.length;
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
