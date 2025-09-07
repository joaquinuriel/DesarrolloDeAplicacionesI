package edu.uade.ritmofit.data.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Class {

    @SerializedName("idClase")
    private String idClase;

    @SerializedName("horarioInicio")
    private String horarioInicio;

    @SerializedName("horarioFin")
    private String horarioFin;

    @SerializedName("duracion")
    private Double duracion;

    @SerializedName("disciplina")
    private String disciplina;

    @SerializedName("idSede")
    private String sede;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("idProfesor")
    private String profesor;

    @SerializedName("cupo")
    private int cupo;

    @SerializedName("estado")
    private String estado;

    @SerializedName("calificaciones")
    private List<String> calificaciones;

    public String getIdClase() { return idClase; }
    public String getHorarioInicio() { return horarioInicio; }
    public String getHorarioFin() { return horarioFin; }
    public Double getDuracion() { return duracion; }
    public String getDisciplina() { return disciplina; }
    public String getIdSede() { return sede; }
    public String getFecha() { return fecha; }
    public String getIdProfesor() { return profesor; }
    public int getCupo() { return cupo; }
    public String getEstado() { return estado; }
    public List<String> getCalificaciones() { return calificaciones; }

    public void setIdClase(String idClase) {
        this.idClase = idClase;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public void setHorarioFin(String horarioFin) {
        this.horarioFin = horarioFin;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCalificaciones(List<String> calificaciones) {
        this.calificaciones = calificaciones;
    }
}
