package com.example.proyectofinal.models;

import com.google.gson.annotations.SerializedName;

public class FavTours {
    @SerializedName("_id")
    public String _id;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
