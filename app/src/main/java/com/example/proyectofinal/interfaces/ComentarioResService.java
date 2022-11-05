package com.example.proyectofinal.interfaces;



import com.example.proyectofinal.DTO.ComentarioResGetResponse;
import com.example.proyectofinal.DTO.ComentarioResResponse;
import com.example.proyectofinal.models.ComentarioRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComentarioResService {


    @GET("api/calRes/{resId}")
    Call<List<ComentarioResGetResponse>> getComentariosRest(@Path("resId") String resId);

    @POST("api/calRes")
    Call<ComentarioResResponse> comentarRestaurante(@Body ComentarioRest comentarioRest);

    @DELETE("api/calRes/{deleteId}")
    Call<ComentarioResResponse> deleteComentarioRest(@Path("deleteId") String deleteId);

}
