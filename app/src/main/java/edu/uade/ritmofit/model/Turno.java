package edu.uade.ritmofit.model;

public class Turno {
    private String clase = "Boxeo";
    private String sede = "Palermo";
    private String fecha = "21-09";

    public Turno(String clase, String sede, String fecha) {
        this.clase = clase;
        this.sede = sede;
        this.fecha = fecha;
    }

    public String getClase() {
        return clase;
    }

    public String getSede() {
        return sede;
    }

    public String getFecha() {
        return fecha;
    }
}
