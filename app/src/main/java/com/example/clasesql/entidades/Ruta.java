package com.example.clasesql.entidades;

import java.io.Serializable;

public class Ruta implements Serializable {
    private String codigo,ciudad,longitud,latitud;

    public Ruta(String codigo, String ciudad, String longitud, String latitud) {
        this.codigo = codigo;
        this.ciudad = ciudad;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
}
