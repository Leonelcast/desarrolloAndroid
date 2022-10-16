package com.example.proyectofinal.models;

import com.google.gson.annotations.SerializedName;

public class ComentarioTour {
    public String _idTour;
    @SerializedName("usuario")
    public String usuario;
    @SerializedName("turistico")
    public String turistico;
    @SerializedName("calificacion")
    public Double calificacionTour;
    @SerializedName("comentario")
    public String comentario;


    public ComentarioTour(){}

    public String get_idTour() {
        return _idTour;
    }

    public void set_idTour(String _idTour) {
        this._idTour = _idTour;
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
