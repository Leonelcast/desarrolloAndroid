package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.models.Tour;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TourService {
    @GET("api/turistico/maspopular/{userID}")
    Call<List<Tour>> getAllTuristicos(@Path("userID")String userID);

}
