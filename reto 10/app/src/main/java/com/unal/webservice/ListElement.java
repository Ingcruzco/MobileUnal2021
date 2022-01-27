package com.unal.webservice;

public class ListElement {
    public String codigoMunicipio;
    public String municipio;
    public String sexo;
    public String fechaInicio;
    public String areaTematica;
    public String duracion;
    public String nombreCurso;

    public ListElement(String codigoMunicipio, String municipio, String sexo, String fechaInicio, String areaTematica, String duracion, String nombreCurso) {
        this.codigoMunicipio = codigoMunicipio;
        this.municipio = municipio;
        this.sexo = sexo;
        this.fechaInicio = fechaInicio;
        this.areaTematica = areaTematica;
        this.duracion = duracion;
        this.nombreCurso = nombreCurso;
    }




    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    public void setAreaTematica(String areaTematica) {
        this.areaTematica = areaTematica;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }





}
