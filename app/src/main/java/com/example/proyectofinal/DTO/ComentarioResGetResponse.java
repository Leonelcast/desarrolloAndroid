package com.example.proyectofinal.DTO;

import com.example.proyectofinal.models.ComentarioRest;
import com.example.proyectofinal.models.User;
import com.google.gson.annotations.SerializedName;

public class ComentarioResGetResponse {
    @SerializedName("_id")
    public String _id;
    @SerializedName("usuario")
    public User user;
    @SerializedName("restaurante")
    public String restaurante;
    @SerializedName("calificacion")
    public Double calificacion;
    @SerializedName("comentario")
    public String comentario;


    public ComentarioResGetResponse(){

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
