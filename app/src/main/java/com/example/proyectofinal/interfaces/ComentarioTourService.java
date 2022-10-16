package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.DTO.ComentarioTourGet;
import com.example.proyectofinal.DTO.ComentarioTourResponse;
import com.example.proyectofinal.models.ComentarioTour;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComentarioTourService {
    @GET("api/calTour/{tourId}")
    Call<List<ComentarioTourGet>> getComentarioTour(@Path("tourId") String tourId);

    @POST("api/calTour")
    Call<ComentarioTourResponse> comentarTour(@Body ComentarioTour comentarioTour);

    @DELETE("api/calTour/{deleteTour}")
    Call<ComentarioTourResponse> deleteComentarioTour(@Path("deleteTour") String deleteTour);
}
