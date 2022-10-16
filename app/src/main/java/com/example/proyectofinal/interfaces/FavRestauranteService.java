package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.DTO.FavoritoRestauranteResponse;
import com.example.proyectofinal.DTO.RestaurantesFavGet;
import com.example.proyectofinal.models.FavRestaurantes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavRestauranteService {
    @GET("api/favRes/{favResId}")
    Call<List<RestaurantesFavGet>> getFavoritos(@Path("favResId")String favResId);


    @POST("api/favRes")
    Call<FavoritoRestauranteResponse> agregarFavorito(@Body FavRestaurantes favRestaurantes);


}
