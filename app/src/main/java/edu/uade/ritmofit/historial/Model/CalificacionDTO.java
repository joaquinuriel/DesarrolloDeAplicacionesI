package edu.uade.ritmofit.historial.Model;

public class CalificacionDTO {

    private String idCalificacion;
    private String idUsuario;
    private String idClase;
    private String idProfesor;
    private String comentario;
    private int estrellas;
    private String timestamp;
    public CalificacionDTO(String idCalificacion, String idUsuario, String idClase, String idProfesor, String comentario, int estrellas, String timestamp) {
        this.idCalificacion = idCalificacion;
        this.idUsuario = idUsuario;
        this.idClase = idClase;
        this.idProfesor = idProfesor;
        this.comentario = comentario;
        this.estrellas = estrellas;
        this.timestamp = timestamp;
    }
    public String getIdCalificacion() {
        return idCalificacion;
    }
    public String getIdUsuario() {
        return idUsuario;
    }
    public String getIdClase() {
        return idClase;
    }
    public String getIdProfesor() {
        return idProfesor;
    }
    public String getComentario() {
        return comentario;
    }
    public int getEstrellas() {
        return estrellas;
    }
    public String getTimestamp() {
        return timestamp;
    }

}
