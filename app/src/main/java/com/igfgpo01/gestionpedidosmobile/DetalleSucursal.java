package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.igfgpo01.gestionpedidosmobile.responses.SucursalResponse;

public class DetalleSucursal extends AppCompatActivity {

    private TextView txtSucurNombre;
    private TextView txtSucurDireccion;
    private Button btnChat;
    private Button btnOrdenar;

    private SucursalResponse sucursal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_sucursal);

        this.txtSucurNombre = (TextView) findViewById(R.id.txt_detalle_nombre);
        this.txtSucurDireccion = (TextView) findViewById(R.id.txt_detalle_direccion);
        this.btnChat = (Button) findViewById(R.id.btn_detalle_chat);
        this.btnOrdenar = (Button) findViewById(R.id.btn_detalle_ordenar);

        //Obtener parametros del Intent, los datos de la sucursal que se está mostrando
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            sucursal = (SucursalResponse) bundle.getSerializable(SucursalResponse.KEY);
            txtSucurNombre.append(" " + sucursal.getNombre());
            txtSucurDireccion.append(" " + sucursal.getDireccion());
        }

        this.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        this.btnOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RealizarOrdenActivity.class);
                startActivity(intent);
            }
        });
    }
}
