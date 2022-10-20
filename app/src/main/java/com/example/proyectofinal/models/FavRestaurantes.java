package com.example.proyectofinal.models;

import com.google.gson.annotations.SerializedName;

public class FavRestaurantes {
    @SerializedName("usuario")
    public String usuario;
    @SerializedName("restaurante")
    public String restaurante;
    @SerializedName("favoritos")
    public Boolean favoritos;
    @SerializedName("date")
    public String date;


    public FavRestaurantes(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public Boolean getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Boolean favoritos) {
        this.favoritos = favoritos;
    }
}
