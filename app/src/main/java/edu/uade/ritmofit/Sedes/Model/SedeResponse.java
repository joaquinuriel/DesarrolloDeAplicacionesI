package edu.uade.ritmofit.Sedes.Model;

public class SedeResponse {

    private String id_sede;
    private String nombre;
    private String ubicacion;

    private String barrio;

    // Constructor vacío (necesario para Gson)
    public SedeResponse() {
    }

    // Constructor con parámetros
    public SedeResponse(String id_sede, String nombre, String ubicacion,String barrio) {
        this.id_sede = id_sede;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.barrio=barrio;
    }

    // Getters y Setters
    public String getId_sede() {
        return id_sede;
    }

    public void setId_sede(String id_sede) {
        this.id_sede = id_sede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getBarrio() {
        return barrio;
    }
    @Override
    public String toString() {
        return nombre + " - " + ubicacion;
    }

}
