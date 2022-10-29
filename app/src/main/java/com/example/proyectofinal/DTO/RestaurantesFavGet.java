package com.example.proyectofinal.DTO;

import com.example.proyectofinal.models.Restaurante;
import com.google.gson.annotations.SerializedName;

public class RestaurantesFavGet {
    @SerializedName("_id")
    public String _id;
    @SerializedName("restaurante")
    public Restaurante restaurante;
    @SerializedName("usuario")
    public String usuario;
    @SerializedName("date")
    public String date;
    @SerializedName("favoritos")
    public Boolean favoritos;

    RestaurantesFavGet(){}


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Boolean favoritos) {
        this.favoritos = favoritos;
    }
}
