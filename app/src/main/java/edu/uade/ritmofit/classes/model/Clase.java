package edu.uade.ritmofit.classes.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Clase implements Serializable {

    @SerializedName("idClase")
    private String idClase;

    @SerializedName("horarioInicio")
    private String horarioInicio;

    @SerializedName("horarioFin")
    private String horarioFin;

    @SerializedName("duracion")
    private double duracion;

    @SerializedName("disciplina")
    private String disciplina;

    @SerializedName("idSede")
    private String idSede;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("idProfesor")
    private String idProfesor;

    @SerializedName("cupo")
    private int cupo;

    @SerializedName("estado")
    private String estado;

    @SerializedName("calificaciones")
    private List<String> calificaciones;

    private String sedeNombre;
    private String profesorNombre;

    public String getSedeNombre() { return sedeNombre; }
    public void setSedeNombre(String sedeNombre) { this.sedeNombre = sedeNombre; }

    public String getProfesorNombre() { return profesorNombre; }
    public void setProfesorNombre(String profesorNombre) { this.profesorNombre = profesorNombre; }


    // Getters y setters
    public String getIdClase() { return idClase; }
    public void setIdClase(String idClase) { this.idClase = idClase; }

    public String getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(String horarioInicio) { this.horarioInicio = horarioInicio; }

    public String getHorarioFin() { return horarioFin; }
    public void setHorarioFin(String horarioFin) { this.horarioFin = horarioFin; }

    public double getDuracion() { return duracion; }
    public void setDuracion(double duracion) { this.duracion = duracion; }

    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

    public String getIdSede() { return idSede; }
    public void setIdSede(String idSede) { this.idSede = idSede; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getIdProfesor() { return idProfesor; }
    public void setIdProfesor(String idProfesor) { this.idProfesor = idProfesor; }

    public int getCupo() { return cupo; }
    public void setCupo(int cupo) { this.cupo = cupo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<String> getCalificaciones() { return calificaciones; }
    public void setCalificaciones(List<String> calificaciones) { this.calificaciones = calificaciones; }
}
