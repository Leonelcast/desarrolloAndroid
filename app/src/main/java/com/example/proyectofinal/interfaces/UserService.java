package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.DTO.FavoritoTourResponse;
import com.example.proyectofinal.DTO.UserResponse;
import com.example.proyectofinal.models.FavTours;
import com.example.proyectofinal.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @POST("api/usuarios")
    Call<UserResponse> signUpUser(@Body User user);

    @POST("api/login")
    Call<UserResponse> login(@Body User user);

    @PUT("api/usuarios/{updateId}")
    Call<UserResponse> updateuser(@Path("updateId") String updateId, @Body User user);

    @PUT("api/usuarios/cambioPassword/{updateId}")
    Call<UserResponse> updateuserPassword(@Path("updateId") String updateId, @Body User user);

}
