package com.example.proyectofinal.models;

import com.google.gson.annotations.SerializedName;

public class FavTours {
    @SerializedName("usuario")
    public String usuario;
    @SerializedName("turistico")
    public String turistico;
    @SerializedName("favoritos")
    public Boolean favoritos;
    @SerializedName("date")
    public String date;

    public FavTours(){

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTuristico() {
        return turistico;
    }

    public void setTuristico(String turistico) {
        this.turistico = turistico;
    }

    public Boolean getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Boolean favoritos) {
        this.favoritos = favoritos;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
