package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MiPerfilActivity extends AppCompatActivity {

    private TextView txtNombre;
    private TextView txtApellido;
    private TextView txtDireccion;
    private TextView txtEmail;
    private TextView txtPassword;
    private Button btnEditar;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        this.txtNombre = (TextView) findViewById(R.id.txt_perfil_nombre);
        this.txtApellido = (TextView) findViewById(R.id.txt_perfil_apellido);
        this.txtDireccion = (TextView) findViewById(R.id.txt_perfil_direccion);
        this.txtEmail = (TextView) findViewById(R.id.txt_perfil_email);
        this.txtPassword = (TextView) findViewById(R.id.txt_perfil_password);
        this.btnEditar = (Button) findViewById(R.id.btn_perfil_editar);
        this.btnGuardar = (Button) findViewById(R.id.btn_perfil_guardar);

        this.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.INVISIBLE);
                btnGuardar.setVisibility(View.VISIBLE);

                txtNombre.setEnabled(true);
                txtApellido.setEnabled(true);
                txtDireccion.setEnabled(true);
                txtEmail.setEnabled(true);
                txtPassword.setEnabled(true);
            }
        });

        this.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
