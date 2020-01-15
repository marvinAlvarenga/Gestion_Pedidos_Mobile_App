package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.igfgpo01.gestionpedidosmobile.responses.ListadoSucursalesResponse;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

public class DetalleSucursal extends AppCompatActivity {

    private TextView txtSucurNombre;
    private TextView txtSucurDireccion;
    private Button btnChat;
    private Button btnVerMenu;

    private ListadoSucursalesResponse sucursal; //la sucursal de la que se está mostrando el detalle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_sucursal);

        this.txtSucurNombre = (TextView) findViewById(R.id.txt_detalle_nombre);
        this.txtSucurDireccion = (TextView) findViewById(R.id.txt_detalle_direccion);
        this.btnChat = (Button) findViewById(R.id.btn_detalle_chat);
        this.btnVerMenu = (Button) findViewById(R.id.btn_detalle_ver_menu);

        //Obtener parametros del Intent, los datos de la sucursal que se está mostrando
        int idSucursal = getIntent().getIntExtra(ListadoSucursalesResponse.KEY, -1);
        if(idSucursal > 0) {
            sucursal = ChatSingleton.getInstance().getSucursalById(idSucursal);
            txtSucurNombre.append(" " + sucursal.getNombre());
            txtSucurDireccion.append(" " + sucursal.getDireccion());
        }

        this.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetTest.isOnline(view.getContext())) {
                    Intent intent = new Intent(view.getContext(), ChatActivity.class);
                    intent.putExtra(BandejaEntradaActivity.KEY_CHAT_SELECCIONADO, sucursal.getId());
                    startActivity(intent);
                } else Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
            }
        });

        this.btnVerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sucursal != null) {
                    if(InternetTest.isOnline(getApplicationContext())){
                        Intent intent = new Intent(view.getContext(), MenusActivity.class);
                        intent.putExtra(ListadoSucursalesResponse.KEY, sucursal.getId());
                        startActivity(intent);
                    }else Toast.makeText(getApplicationContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();

                } else Toast.makeText(getApplicationContext(), R.string.txt_detalle_sin_menu_disponible, Toast.LENGTH_LONG).show();

            }
        });
    }
}
