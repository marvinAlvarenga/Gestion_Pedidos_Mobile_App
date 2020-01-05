package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igfgpo01.gestionpedidosmobile.responses.SessionResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;
import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView txtEmail;
    private TextView txtPassword;
    private Button btnIngresar;
    private Button btnRegistro;
    private ProgressBar barLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.txtEmail = (TextView) findViewById(R.id.txtEmail);
        this.txtPassword = (TextView) findViewById(R.id.txtPassword);
        this.btnIngresar = (Button) findViewById(R.id.btn_ingresar);
        this.btnRegistro = (Button) findViewById(R.id.btn_registrarse);
        this.barLogin = (ProgressBar) findViewById(R.id.bar_login);

        this.btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });

        this.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(InternetTest.isOnline(view.getContext())){
                String userName = txtEmail.getText().toString();
                String pass = txtPassword.getText().toString();

                if(userName.isEmpty() || pass.isEmpty()){
                    Toast.makeText(view.getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                    String auxKey = userName + ":" + pass;
                    new LoginTask().execute(auxKey); //Intentar hacer login remoto
                }
            }else {
                Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    //Llamada a la API
    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            barLogin.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String auxKey = strings[0]; //recuperar key pasada como parámetro
            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<SessionResponse> call = service.login(auxKey);
            boolean hasValidKey = false;
            try {

                Response<SessionResponse> res = call.execute();
                SessionResponse r = res.body();
                hasValidKey = Boolean.parseBoolean(r.getLogin());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return hasValidKey ? auxKey : null;
        }

        @Override
        protected void onPostExecute(String string) {
            if(string != null){
                SharedPreferences preferences = getApplicationContext().getSharedPreferences(SessionLocalSingleton.getInstance().FILE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SessionLocalSingleton.getInstance().API_KEY, string);
                editor.commit();
                getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Credenciales no válidas!", Toast.LENGTH_SHORT).show();
            }

            barLogin.setVisibility(View.GONE);
        }
    }
}
