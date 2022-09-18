package com.example.proyectofinal.interfaces;

import com.example.proyectofinal.DTO.UserResponse;
import com.example.proyectofinal.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("api/usuarios")
    Call<UserResponse> signUpUser(@Body User user);

    @POST("api/login")
    Call<UserResponse> login(@Body User user);
}
