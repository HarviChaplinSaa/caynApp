package com.cayn.caynapp.modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Evento implements Serializable {

    private String id_creador;
    private String id_evento = "";
    private List<String> id_asistentes;
    private String descripcion;
    private String cantidad_asistentes;
    private String imagen;
    private String nombre;
    private String fecha_fin;
    private String fecha_inicio;
    private String hora_fin;
    private String hora_inicio;
    private Boolean tipo;
    private String ruta;

    public Evento() {
    }

    public String getCantidad_asistentes() {
        return cantidad_asistentes;
    }

    public void setCantidad_asistentes(String cantidad_asistentes) {
        this.cantidad_asistentes = cantidad_asistentes;
    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getId_creador() {
        return id_creador;
    }

    public void setId_creador(String id_creador) {
        this.id_creador = id_creador;
    }

    public List<String> getId_asistentes() {
        return id_asistentes;
    }

    public void setId_asistentes(List<String> id_asistentes) {
        this.id_asistentes = id_asistentes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id_creador='" + id_creador + '\'' +
                ", id_asistentes=" + id_asistentes +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_fin='" + fecha_fin + '\'' +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", hora_fin='" + hora_fin + '\'' +
                ", hora_inicio='" + hora_inicio + '\'' +
                ", tipo=" + tipo +
                ", ruta='" + ruta + '\'' +
                '}';
    }
}
