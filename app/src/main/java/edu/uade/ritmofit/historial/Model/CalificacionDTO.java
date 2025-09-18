package edu.uade.ritmofit.historial.Model;

import java.time.LocalDateTime;

public class CalificacionDTO {
    private String idCalificacion;
    private String idUsuario;
    private String idClase;
    private String idProfesor;
    private String comentario;

    private int estrellas;

    private LocalDateTime timestamp;
    public CalificacionDTO() {
    }
    public CalificacionDTO(String idCalificacion, String idUsuario, String idClase, String idProfesor, String comentario, int estrellas, LocalDateTime timestamp) {
        this.idCalificacion = idCalificacion;
        this.idUsuario = idUsuario;
        this.idClase = idClase;
        this.idProfesor = idProfesor;
        this.comentario = comentario;
        this.estrellas = estrellas;
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

}
