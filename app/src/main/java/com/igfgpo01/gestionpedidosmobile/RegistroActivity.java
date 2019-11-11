package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RegistroActivity extends AppCompatActivity {

    private TextView txtEmail;
    private TextView txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.txtEmail = (TextView) findViewById(R.id.txt_reg_email);
        this.txtPassword = (TextView) findViewById(R.id.txt_reg_password);
    }
}
