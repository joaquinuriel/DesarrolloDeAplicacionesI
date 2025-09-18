package edu.uade.ritmofit.historial.Model.ui.Adapter;

public class HistorialReservaAdapter {
    private String idReserva;
    private String fecha;
    private String disciplina;
    private String sede;

    public HistorialReservaAdapter(String idReserva, String fecha, String disciplina, String sede) {
        this.idReserva = idReserva;
        this.fecha = fecha;
        this.disciplina = disciplina;
        this.sede = sede;
    }
    public String getIdReserva() { return idReserva; }
    public String getFecha() { return fecha ;}
    public String getDisciplina() { return disciplina; }

    public String getSede() { return sede; }

}
