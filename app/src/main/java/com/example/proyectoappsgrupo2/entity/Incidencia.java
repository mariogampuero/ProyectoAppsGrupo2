package com.example.proyectoappsgrupo2.entity;

public class Incidencia {


    private String autor;
    private String nombre;
    private String descripcion;
    private double latitud;
    private double longitud;
    private String foto;
    private String estado;
    private String comentario;

    public Incidencia(){}

    public Incidencia(String url, String desc, String nombre, String autor,String estado,
                      double lat, double lon, String comentario) {
        this.nombre = nombre;
        this.autor = autor;
        this.foto = url;
        this.latitud = lat;
        this.longitud = lon;
        this.descripcion = desc;
        this.estado = estado;
        this.comentario = comentario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
