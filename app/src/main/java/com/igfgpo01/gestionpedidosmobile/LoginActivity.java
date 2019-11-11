package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView txtEmail;
    private TextView txtPassword;
    private Button btnIngresar;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.txtEmail = (TextView) findViewById(R.id.txtEmail);
        this.txtPassword = (TextView) findViewById(R.id.txtPassword);
        this.btnIngresar = (Button) findViewById(R.id.btn_ingresar);
        this.btnRegistro = (Button) findViewById(R.id.btn_registrarse);

        this.btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });
    }
}
