package edu.uade.ritmofit.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Profesor {

    @SerializedName("idProfesor")
    private String idProfesor;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("clases")
    private List<String> clases;

    @SerializedName("calificaciones")
    private List<String> calificaciones;

    // Getters y Setters
    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getClases() {
        return clases;
    }

    public void setClases(List<String> clases) {
        this.clases = clases;
    }

    public List<String> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<String> calificaciones) {
        this.calificaciones = calificaciones;
    }
}