package com.example.proyectofinal.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Restaurante {
    @SerializedName("_id")
    public String _id;
    @SerializedName("nombre")
    public String nombre;
    @SerializedName("departamento")
    public String departamento;
    @SerializedName("calificacion")
    public String calificacion = "0";
    @SerializedName("img")
    public String img;
    @SerializedName("lat")
    public String lat;
    @SerializedName("long")
    public String longitud;
    @SerializedName("descripcion")
    public String descripcion;
   // @SerializedName("favoritos")
   // public Boolean favoritos;
    @SerializedName("favoritosRest")
    public ArrayList<FavRestaurantes> favRestaurantes;



    public Restaurante(){

    }
    public Restaurante(String _id, String nombre, String departamento, String calificacion, String img, String descripcion, String longitud, String lat, boolean favoritos){
        this._id = _id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.calificacion = calificacion;
        this.img = img;
        this.descripcion = descripcion;
        this.longitud = longitud;
        this.lat = lat;
        //this.favoritos = favoritos;
    }

    public ArrayList<FavRestaurantes> getFavRestaurantes() {
        return favRestaurantes;
    }

    public void setFavRestaurantes(ArrayList<FavRestaurantes> favRestaurantes) {
        this.favRestaurantes = favRestaurantes;
    }
    /*  public Boolean getFavoritos() {
        return favoritos;
    }*/

   /* public void setFavoritos(Boolean favoritos) {
        this.favoritos = favoritos;
    }*/

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
}
