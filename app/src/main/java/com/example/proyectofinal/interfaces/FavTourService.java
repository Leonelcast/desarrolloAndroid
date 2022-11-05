package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.DTO.FavoritoRestauranteResponse;
import com.example.proyectofinal.DTO.FavoritoTourResponse;
import com.example.proyectofinal.DTO.RestaurantesFavGet;
import com.example.proyectofinal.DTO.TourFavGet;
import com.example.proyectofinal.models.FavRestaurantes;
import com.example.proyectofinal.models.FavTours;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FavTourService {

    @GET("api/favTour/{favResId}")
    Call<List<TourFavGet>> getFavoritos(@Path("favResId")String favTourId);


    @POST("api/favTour")
    Call<FavoritoTourResponse> agregarFavorito(@Body FavTours favTours);

    @DELETE("api/favTour/{deleteId}")
    Call<FavoritoTourResponse> deleteFavRest(@Path("deleteId") String deleteId);
    @PUT("api/favTour/{updateId}")
    Call<FavoritoTourResponse> updateFavRest(@Path("updateId") String updateId, @Body FavTours favTours);

}
