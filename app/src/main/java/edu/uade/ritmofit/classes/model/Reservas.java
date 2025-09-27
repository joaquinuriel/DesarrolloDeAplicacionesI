package edu.uade.ritmofit.classes.model;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;


public class Reservas implements Serializable {
    @SerializedName("idReserva")
    private String idReserva;

    @SerializedName("idClase")
    private String idClase;

    @SerializedName("idUsuario")
    private String idUsuario;

    @SerializedName("estado")
    private String estado;


    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdClase() {
        return idClase;
    }

    public void setIdClase(String idClase) {
        this.idClase = idClase;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
