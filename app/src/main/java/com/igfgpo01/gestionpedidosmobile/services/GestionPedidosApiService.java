package com.igfgpo01.gestionpedidosmobile.services;

import com.igfgpo01.gestionpedidosmobile.responses.SessionResponse;
import com.igfgpo01.gestionpedidosmobile.responses.SucursalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GestionPedidosApiService {

    //Rutas para verificar credenciales
    @POST("api/login_api/")
    Call<SessionResponse> login(@Query("apikey") String apiKey);

    @POST("api/log/appLogout/")
    Call<SessionResponse> logOut(@Query("apiKey") String apiKey);

    //Obtener todas las sucursales
    @GET("sucursal/sucursales/")
    Call<List<SucursalResponse>> getAllSucursales();

}
