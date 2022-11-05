package com.example.proyectofinal.models;

import com.google.gson.annotations.SerializedName;

public class ComentarioRest {
   public String _id;
  @SerializedName("usuario")
   public String usuario;
   @SerializedName("restaurante")
    public String restaurante;
   @SerializedName("calificacion")
    public Double calificacion;
   @SerializedName("comentario")
    public String comentario;
   /* @SerializedName("usuario")
   public User usuarios;

    public User getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(User usuarios) {
        this.usuarios = usuarios;
    }*/

    public ComentarioRest(){

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
