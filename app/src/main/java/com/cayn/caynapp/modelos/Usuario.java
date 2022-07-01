package com.cayn.caynapp.modelos;

import java.util.List;

public class Usuario {

    private String correo;
    private String edad;
    private String nombre;
    private String fecha;
    private int sexo;

    public Usuario() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "correo='" + correo + '\'' +
                ", edad='" + edad + '\'' +
                ", nombre='" + nombre + '\'' +
                ", sexo=" + sexo +
                '}';
    }
}
