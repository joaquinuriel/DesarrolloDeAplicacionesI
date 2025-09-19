package edu.uade.ritmofit.historial.Model.ui.Adapter;


import java.util.List;

import edu.uade.ritmofit.historial.Model.CalificacionDTO;

public class HistorialReservaAdapterDetail {
    private String idReserva;
    private String estado;
    private String timestampCreacion;
    private String timestampCheckin;
    private boolean confirmedCheckin;
    private String fechaClase;
    private String fechaClaseDuracion;
    private String disciplina;
    private List<CalificacionDTO> calificaciones;

    private String sedeNombre;
    private String profesorNombre;

    public HistorialReservaAdapterDetail() {}
    public HistorialReservaAdapterDetail(String idReserva, String estado, String timestampCreacion,
                                         String timestampCheckin, boolean confirmedCheckin,
                                         String fechaClase, String fechaClaseDuracion,
                                         String disciplina, List<CalificacionDTO> calificaciones,
                                         String sedeNombre, String profesorNombre) {
        this.idReserva = idReserva;
        this.estado = estado;
        this.timestampCreacion = timestampCreacion;
        this.timestampCheckin = timestampCheckin;
        this.confirmedCheckin = confirmedCheckin;
        this.fechaClase = fechaClase;
        this.fechaClaseDuracion = fechaClaseDuracion;
        this.disciplina = disciplina;
        this.calificaciones = calificaciones;

        this.sedeNombre = sedeNombre;
        this.profesorNombre = profesorNombre;
    }

    // Getters
    public String getIdReserva() {
        return idReserva;
    }

    public String getEstado() {
        return estado;
    }

    public String getTimestampCreacion() {
        return timestampCreacion;
    }

    public String getTimestampCheckin() {
        return timestampCheckin;
    }

    public boolean isConfirmedCheckin() {
        return confirmedCheckin;
    }

    public String getFechaClase() {
        return fechaClase;
    }

    public String getFechaClaseDuracion() {
        return fechaClaseDuracion;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public List<CalificacionDTO> getCalificaciones () {
        return calificaciones;
    }



    public String getSedeNombre() {
        return sedeNombre;
    }

    public String getProfesorNombre() {
        return profesorNombre;
    }

    // Setters
    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTimestampCreacion(String timestampCreacion) {
        this.timestampCreacion = timestampCreacion;
    }

    public void setTimestampCheckin(String timestampCheckin) {
        this.timestampCheckin = timestampCheckin;
    }

    public void setConfirmedCheckin(boolean confirmedCheckin) {
        this.confirmedCheckin = confirmedCheckin;
    }

    public void setFechaClase(String fechaClase) {
        this.fechaClase = fechaClase;
    }

    public void setFechaClaseDuracion(String fechaClaseDuracion) {
        this.fechaClaseDuracion = fechaClaseDuracion;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setCalificaciones(List<CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }


    public void setSedeNombre(String sedeNombre) {
        this.sedeNombre = sedeNombre;
    }

    public void setProfesorNombre(String profesorNombre) {
        this.profesorNombre = profesorNombre;
    }


}