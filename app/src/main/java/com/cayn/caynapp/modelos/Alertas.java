package com.cayn.caynapp.modelos;

public class Alertas {

    private String lat;
    private String lon;
    private String hora;
    private String fecha;
    private String causa;

    public Alertas() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    @Override
    public String toString() {
        return "Alertas{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", hora='" + hora + '\'' +
                ", fecha='" + fecha + '\'' +
                ", causa='" + causa + '\'' +
                '}';
    }
}
