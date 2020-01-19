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

import com.igfgpo01.gestionpedidosmobile.responses.EditarPerfilResponse;
import com.igfgpo01.gestionpedidosmobile.responses.SessionResponse;
import com.igfgpo01.gestionpedidosmobile.responses.VerPerfilResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;
import com.igfgpo01.gestionpedidosmobile.singleton.ChatSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;
import com.igfgpo01.gestionpedidosmobile.singleton.SocketCommunicationSingleton;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiPerfilActivity extends AppCompatActivity {

    private TextView txtUsername;
    private TextView txtDireccion;
    private TextView txtEmail;

    private Button btnEditar;
    private Button btnGuardar;
    private Button btnCerrarSesion;
    private ProgressBar barPerfil;

    private String oldUsername; //para verificar que la llave no ha cambiado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        this.txtUsername = (TextView) findViewById(R.id.txt_perfil_username);
        this.txtDireccion = (TextView) findViewById(R.id.txt_perfil_direccion);
        this.txtEmail = (TextView) findViewById(R.id.txt_perfil_email);
        this.btnEditar = (Button) findViewById(R.id.btn_perfil_editar);
        this.btnGuardar = (Button) findViewById(R.id.btn_perfil_guardar);
        this.btnCerrarSesion = (Button) findViewById(R.id.btn_perfil_cerrar_sesion);
        this.barPerfil = (ProgressBar) findViewById(R.id.bar_perfil);

        //Intentar consultar la informacion del perfil
        if(InternetTest.isOnline(this))
            new VerPerfilTask().execute(SessionLocalSingleton.getInstance().getApiKey(this));
        else Toast.makeText(this, R.string.sin_internet, Toast.LENGTH_SHORT).show();

        this.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldUsername = txtUsername.getText().toString(); //Username antiguo

                view.setVisibility(View.INVISIBLE);
                btnGuardar.setVisibility(View.VISIBLE);

                txtUsername.setEnabled(true);
                txtDireccion.setEnabled(true);
                txtEmail.setEnabled(true);
            }
        });

        this.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);

                String username, email, direccion;
                username = txtUsername.getText().toString();
                email = txtEmail.getText().toString();
                direccion = txtDireccion.getText().toString();

                if(username.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
                    Toast.makeText(view.getContext(), R.string.txt_perfil_formulario_incompleto, Toast.LENGTH_SHORT).show();
                } else if(!InternetTest.isOnline(view.getContext())) {
                    Toast.makeText(view.getContext(), R.string.sin_internet, Toast.LENGTH_SHORT).show();
                } else {
                    //Intentar actualizar los datos del usuario
                    new ActualizarPerfilTask().execute(username, email, direccion);
                }
            }
        });

        this.btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = SessionLocalSingleton.getInstance().getApiKey(view.getContext());

                if(InternetTest.isOnline(view.getContext())){
                    GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
                    Call<SessionResponse> call = service.logOut(key);
                    call.enqueue(new Callback<SessionResponse>() {
                        @Override
                        public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                            //SessionResponse res = response.body();
                        }

                        @Override
                        public void onFailure(Call<SessionResponse> call, Throwable t) {

                        }
                    });
                }

                //Borrar la key de las preferencias de la aplicación
                SessionLocalSingleton.getInstance().guardarKey(view.getContext(), "");

                //Borrar el id del usuario
                SessionLocalSingleton.getInstance().guardarIdUserLoged(view.getContext(), 0);

                //Liberar el singleton de chats
                ChatSingleton.setINSTANCE(null);

                //Liberar el socket
                SocketCommunicationSingleton.setINSTANCE(null);

                startActivity(new Intent(view.getContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }

        });

    }

    //Tarea asincrona de recuperar los datos personales
    private class VerPerfilTask extends AsyncTask<String, Void, VerPerfilResponse> {

        @Override
        protected void onPreExecute() {
            barPerfil.setVisibility(View.VISIBLE);
        }

        @Override
        protected VerPerfilResponse doInBackground(String... strings) {
            VerPerfilResponse data = null;
            String key = strings[0];

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<VerPerfilResponse> call = service.verPerfil(key);

            try {
                Response<VerPerfilResponse> response = call.execute();
                data = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(VerPerfilResponse verPerfilResponse) {
            if(verPerfilResponse != null) {
                txtUsername.setText(verPerfilResponse.getUsername());
                txtDireccion.setText(verPerfilResponse.getDireccion());
                txtEmail.setText(verPerfilResponse.getEmail());
            } else Toast.makeText(getApplicationContext(), R.string.txt_perfil_obtener_datos_fallo, Toast.LENGTH_SHORT).show();

            barPerfil.setVisibility(View.GONE);
        }
    }

    //Tarea asincrona para actualizar los datos personales
    private class ActualizarPerfilTask extends AsyncTask<String, String, EditarPerfilResponse> {

        @Override
        protected void onPreExecute() {
            barPerfil.setVisibility(View.VISIBLE);
        }

        @Override
        protected EditarPerfilResponse doInBackground(String... strings) {

            String username, email, direccion, key;
            username = strings[0];
            email = strings[1];
            direccion = strings[2];
            key = SessionLocalSingleton.getInstance().getApiKey(getApplicationContext());

            EditarPerfilResponse data = null;

            GestionPedidosApiService service = RetrofitClientInstance.getRetrofitInstance().create(GestionPedidosApiService.class);
            Call<EditarPerfilResponse> call = service.editarPerfil(key, email, username, direccion);

            try {
                Response<EditarPerfilResponse> response = call.execute();
                data = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(data != null && Boolean.parseBoolean(data.getEstado()))
                publishProgress(username); //enviar username guardado para verificar si ha cambiado
            return data;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            String nuevoUsername = values[0];
            if(!oldUsername.equals(nuevoUsername)) {
                String keyVieja = SessionLocalSingleton.getInstance().getApiKey(getApplicationContext());
                int indiceSeparador = keyVieja.indexOf(":");

                int lonitud = keyVieja.length();
                String nuevaKey = nuevoUsername + keyVieja.substring(indiceSeparador, lonitud);

                SessionLocalSingleton.getInstance().guardarKey(getApplicationContext(), nuevaKey);
            }
        }

        @Override
        protected void onPostExecute(EditarPerfilResponse editarPerfilResponse) {
            if(editarPerfilResponse != null && Boolean.parseBoolean(editarPerfilResponse.getEstado())) {
                txtUsername.setEnabled(false);
                txtDireccion.setEnabled(false);
                txtEmail.setEnabled(false);
                btnGuardar.setVisibility(View.INVISIBLE);
                btnEditar.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), R.string.txt_perfil_actualizar_datos_exito, Toast.LENGTH_LONG).show();
            } else Toast.makeText(getApplicationContext(), R.string.txt_perfil_actualizar_datos_fallo, Toast.LENGTH_SHORT).show();

            barPerfil.setVisibility(View.GONE);
            btnGuardar.setEnabled(true);
        }
    }
}
