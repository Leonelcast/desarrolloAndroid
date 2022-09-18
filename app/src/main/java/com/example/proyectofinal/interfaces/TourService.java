package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.models.Tour;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface TourService {
    @GET("api/turistico/maspopular")
    Call<List<Tour>> getAllTuristicos();
    @GET("api/turistico/menospopular")
    Call<List<Tour>> getMenosPoupular();
    @GET("api/turistico/ascendente")
    Call<List<Tour>> getNombreAs();
    @GET("api/turistico/descendente")
    Call<List<Tour>> getNombreDes();
    @GET("api/turistico/ascendenteDep")
    Call<List<Tour>> getDepAs();
    @GET("api/turistico/descendenteDep")
    Call<List<Tour>> getDepDes();
}
