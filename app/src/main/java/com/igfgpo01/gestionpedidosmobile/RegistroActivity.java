package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igfgpo01.gestionpedidosmobile.responses.RegistroResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private TextView txtEmail;
    private TextView txtPassword;
    private TextView txtUsername;
    private TextView txtDireccion;
    private Button btnRegistro;
    private ProgressBar barRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.txtEmail = (TextView) findViewById(R.id.txt_reg_email);
        this.txtPassword = (TextView) findViewById(R.id.txt_reg_password);
        this.txtUsername = (TextView) findViewById(R.id.txt_reg_username);
        this.txtDireccion = (TextView) findViewById(R.id.txt_reg_direccion);
        this.btnRegistro = (Button) findViewById(R.id.btn_reg_registro);
        this.barRegistro = (ProgressBar) findViewById(R.id.bar_registro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, pass, username, direccion;
                email = txtEmail.getText().toString();
                pass = txtPassword.getText().toString();
                username = txtUsername.getText().toString();
                direccion = txtDireccion.getText().toString();

                if(email.isEmpty() || pass.isEmpty() || username.isEmpty() || direccion.isEmpty()) {
                    Toast.makeText(view.getContext(), R.string.txt_reg_campos_vacios, Toast.LENGTH_SHORT).show();
                } else if(!InternetTest.isOnline(view.getContext())){
                    Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
                } else {
                    //Intentar realizar el registro del nuevo usuario
                    new RegistroTask().execute(email, pass, username, direccion);
                }

            }
        });
    }

    //Llamada a la API
    private class RegistroTask extends AsyncTask<String, Void, RegistroResponse> {

        @Override
        protected void onPreExecute() {
            btnRegistro.setEnabled(false);
            barRegistro.setVisibility(View.VISIBLE);
        }

        @Override
        protected RegistroResponse doInBackground(String... strings) {
            String email, pass, username, direccion;
            email = strings[0];
            pass = strings[1];
            username = strings[2];
            direccion = strings[3];
            RegistroResponse result = null;

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<RegistroResponse> call = service.registrar(email, direccion, pass, username);

            try {
                Response<RegistroResponse> response = call.execute();
                result = response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(RegistroResponse registroResponse) {
            barRegistro.setVisibility(View.GONE);
            if(registroResponse != null && Boolean.parseBoolean(registroResponse.getEstado())) { //Exito en el registro
                txtDireccion.setText("");
                txtEmail.setText("");
                txtPassword.setText("");
                txtUsername.setText("");
                Toast.makeText(getApplicationContext(), R.string.txt_reg_exito, Toast.LENGTH_LONG).show();
            }else Toast.makeText(getApplicationContext(), R.string.txt_reg_fallo, Toast.LENGTH_LONG).show();

            btnRegistro.setEnabled(true);
        }
    }
}
