package edu.uade.ritmofit.data.model;

import com.google.gson.annotations.SerializedName;

public class Sede {

    @SerializedName("id_sede")
    private String idSede;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("ubicacion")
    private String ubicacion;

    @SerializedName("barrio")
    private String barrio;

    // Getters y Setters
    public String getIdSede() {
        return idSede;
    }

    public void setIdSede(String idSede) {
        this.idSede = idSede;
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

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
}
