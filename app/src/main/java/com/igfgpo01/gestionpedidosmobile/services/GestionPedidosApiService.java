package com.igfgpo01.gestionpedidosmobile.services;

import com.igfgpo01.gestionpedidosmobile.responses.SessionResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GestionPedidosApiService {

    @POST("api/login_api/")
    Call<SessionResponse> login(@Query("apikey") String apiKey);

    @POST("api/log/appLogout/")
    Call<SessionResponse> logOut(@Query("apiKey") String apiKey);

}
