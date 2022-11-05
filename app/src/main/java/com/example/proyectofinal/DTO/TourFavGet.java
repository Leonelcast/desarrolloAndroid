package com.example.proyectofinal.DTO;

import com.example.proyectofinal.models.Tour;
import com.google.gson.annotations.SerializedName;

public class TourFavGet {
    @SerializedName("_id")
    public String _id;
    @SerializedName("turistico")
    public Tour tour;
    @SerializedName("usuario")
    public String usuario;
    @SerializedName("date")
    public String date;
    @SerializedName("favoritos")
    public Boolean favoritos;

    TourFavGet(){}

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
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
