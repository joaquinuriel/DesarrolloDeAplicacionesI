package edu.uade.ritmofit.home.model;

public class TurnoHome {
    private String idClase;
    private String estado;
    private String disciplina;
    private String fecha;
    private String idProfesor;
    private String nombreSede;
    private String barrioSede;


    public TurnoHome(String idClase, String estado, String disciplina, String fecha, String idProfesor, String nombreSede, String barrioSede) {
        this.idClase = idClase;
        this.estado = estado;
        this.disciplina = disciplina;
        this.fecha = fecha;
        this.idProfesor = idProfesor;
        this.nombreSede = nombreSede;
        this.barrioSede = barrioSede;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdClase() {
        return idClase;
    }

    public void setIdClase(String idClase) {
        this.idClase = idClase;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getBarrioSede() {
        return barrioSede;
    }

    public void setBarrioSede(String barrioSede) {
        this.barrioSede = barrioSede;
    }
}
