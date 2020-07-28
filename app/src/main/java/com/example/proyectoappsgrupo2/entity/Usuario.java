package com.example.proyectoappsgrupo2.entity;

public class Usuario {

    private String uid;
    private Integer codigo;
    private String correo;
    private String rol;

    public Usuario(){}

    public Usuario(String uid, Integer codigo, String correo, String rol) {
        this.uid = uid;
        this.codigo = codigo;
        this.correo = correo;
        this.rol = rol;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
