package com.igfgpo01.gestionpedidosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.igfgpo01.gestionpedidosmobile.responses.SessionResponse;
import com.igfgpo01.gestionpedidosmobile.services.GestionPedidosApiService;
import com.igfgpo01.gestionpedidosmobile.services.RetrofitClientInstance;
import com.igfgpo01.gestionpedidosmobile.singleton.SessionLocalSingleton;
import com.igfgpo01.gestionpedidosmobile.util.InternetTest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiPerfilActivity extends AppCompatActivity {

    private TextView txtNombre;
    private TextView txtApellido;
    private TextView txtDireccion;
    private TextView txtEmail;
    private TextView txtPassword;
    private Button btnEditar;
    private Button btnGuardar;
    private Button btnCerrarSesion;

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
        this.btnCerrarSesion = (Button) findViewById(R.id.btn_perfil_cerrar_sesion);

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

        this.btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = view.getContext().getSharedPreferences(SessionLocalSingleton.getInstance().FILE_NAME, Context.MODE_PRIVATE);
                String key = preferences.getString(SessionLocalSingleton.getInstance().API_KEY, "");

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
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SessionLocalSingleton.getInstance().API_KEY, "");
                editor.commit();

                startActivity(new Intent(view.getContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }

        });

    }
}
