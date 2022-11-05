package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.models.Restaurante;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestauranteService {
    @GET("api/restaurante/maspopular/{userID}")
    Call<List<Restaurante>> getAllRestaurantes(@Path("userID")String userID);

}
