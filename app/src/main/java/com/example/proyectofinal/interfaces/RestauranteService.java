package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.models.Restaurante;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestauranteService {
    @GET("api/restaurante/maspopular")
    Call<List<Restaurante>> getAllRestaurantes();

}
