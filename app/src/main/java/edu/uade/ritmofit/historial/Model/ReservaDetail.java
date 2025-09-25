package edu.uade.ritmofit.historial.Model;

import java.util.List;

public class ReservaDetail {
/*

 String idReserva;  ReservaDTO
     String estado; ReservaDTO
     String timestampCreacion; ReservaDTO
     String timestampCheckin; ReservaDTO
     boolean confirmedCheckin; ReservaDTO
     String fechaClase; Clase
     String fechaClaseDuracion; Clase
     String disciplina; Clase
     List<CalificacionDTO> calificaciones;

     String sedeNombre; sedeResponse
     String profesorNombre;
 */
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
    public ReservaDetail(String idReserva, String estado, String timestampCreacion, String timestampCheckin, boolean confirmedCheckin, String fechaClase, String fechaClaseDuracion, String disciplina, List<CalificacionDTO> calificaciones, String sedeNombre, String profesorNombre) {
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
    public List<CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }
    public String getSedeNombre() {
        return sedeNombre;
    }
    public String getProfesorNombre() {
        return profesorNombre;
    }

}
