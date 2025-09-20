package edu.uade.ritmofit.historial.Model;

public class Reserva {

    /*
     String idReserva
String disciplina
String nombre
String  fecha

     */
    private String idReserva;
    private String disciplina;
    private String nombreSede;
    private String fecha;

    public Reserva(String idReserva, String disciplina, String nombre, String fecha) {
        this.idReserva = idReserva;
        this.disciplina = disciplina;
        this.nombreSede = nombre;
        this.fecha = fecha;
    }

    public String getIdReserva() {
        return idReserva;
    }
    public String getDisciplina() {
        return disciplina;
    }
    public String getNombre() {
        return nombreSede;
    }
    public String getFecha() {
        return fecha;
    }
}
