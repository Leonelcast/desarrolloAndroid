package com.example.proyectofinal.DTO;

import com.example.proyectofinal.models.User;
import com.google.gson.annotations.SerializedName;

public class ComentarioTourGet {
    @SerializedName("_id")
    public String _idTour;
    @SerializedName("usuario")
    public User user;
    @SerializedName("turistico")
    public String turistico;
    @SerializedName("calificacion")
    public Double calificacionTour;
    @SerializedName("comentario")
    public String comentario;

    public ComentarioTourGet(){}

    public String get_idTour() {
        return _idTour;
    }

    public void set_idTour(String _idTour) {
        this._idTour = _idTour;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTuristico() {
        return turistico;
    }

    public void setTuristico(String turistico) {
        this.turistico = turistico;
    }

    public Double getCalificacionTour() {
        return calificacionTour;
    }

    public void setCalificacionTour(Double calificacionTour) {
        this.calificacionTour = calificacionTour;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
