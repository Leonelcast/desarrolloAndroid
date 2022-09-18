package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.models.Restaurante;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestauranteService {
    @GET("api/restaurante/maspopular")
    Call<List<Restaurante>> getAllRestaurantes();
    @GET("api/restaurante/menospopular")
    Call<List<Restaurante>> getMenosPoupular();
    @GET("api/restaurante/ascendente")
    Call<List<Restaurante>> getNombreAs();
    @GET("api/restaurante/descendente")
    Call<List<Restaurante>> getNombreDes();
    @GET("api/restaurante/ascendenteDep")
    Call<List<Restaurante>> getDepAs();
    @GET("api/restaurante/descendenteDep")
    Call<List<Restaurante>> getDepDes();
}
