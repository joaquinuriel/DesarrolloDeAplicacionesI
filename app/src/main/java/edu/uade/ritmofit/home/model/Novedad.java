package edu.uade.ritmofit.home.model;

public class Novedad {
    private String idNovedad;
    private String titulo;
    private String contenido;
    private String tipo;
    private String fecha;

    public Novedad(String idNovedad, String titulo, String contenido, String tipo, String fecha) {
        this.idNovedad = idNovedad;
        this.titulo = titulo;
        this.contenido = contenido;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(String idNovedad) {
        this.idNovedad = idNovedad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
