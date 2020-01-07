package com.igfgpo01.gestionpedidosmobile.services;

import com.igfgpo01.gestionpedidosmobile.requests.NuevaOrdenRequest;
import com.igfgpo01.gestionpedidosmobile.responses.EditarPerfilResponse;
import com.igfgpo01.gestionpedidosmobile.responses.EnvioOrdenResponse;
import com.igfgpo01.gestionpedidosmobile.responses.MenuResponse;
import com.igfgpo01.gestionpedidosmobile.responses.RegistroResponse;
import com.igfgpo01.gestionpedidosmobile.responses.SessionResponse;
import com.igfgpo01.gestionpedidosmobile.responses.SucursalResponse;
import com.igfgpo01.gestionpedidosmobile.responses.VerPerfilResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GestionPedidosApiService {

    //Rutas para verificar credenciales
    @POST("api/login_api/")
    Call<SessionResponse> login(@Query("apikey") String apiKey);

    @POST("api/log/appLogout/")
    Call<SessionResponse> logOut(@Query("apiKey") String apiKey);

    //Registrar un nuevo usuario
    @POST("registro_api/")
    Call<RegistroResponse> registrar(@Query("email") String email,
                                     @Query("direccion") String direccion,
                                     @Query("password") String password,
                                     @Query("username") String username);

    //Consultar datos del usuario logueado
    @POST("api/perfil/")
    Call<VerPerfilResponse> verPerfil(@Query("apikey") String apiKey);

    //Editar datos del usuario logueado
    @POST("api/editRegistroApi/")
    Call<EditarPerfilResponse> editarPerfil(@Query("apikey") String apiKey,
                                            @Query("email") String email,
                                            @Query("username") String username,
                                            @Query("direccion") String direccion);


    //Obtener todas las sucursales
    @GET("sucursal/sucursales/")
    Call<List<SucursalResponse>> getAllSucursales();

    //Obtener los menus disponibles de una sucursal en particular
    @GET("api/productos_menus/{id}/")
    Call<List<MenuResponse>> getMenusDeSucursal(@Path("id") int id,
                                                @Query("apikey") String apiKey);

    //Enviar una nueva orden
    @POST("api/log/enviarPedidos/")
    Call<EnvioOrdenResponse> enviarOrdenNueva(@Query("apikey") String apiKey,
                                              @Body NuevaOrdenRequest nuevaOrden);

}
