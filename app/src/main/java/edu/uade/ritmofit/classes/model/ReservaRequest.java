package edu.uade.ritmofit.classes.model;

public class ReservaRequest {
    private String idClase;
    private String idUsuario;
    private String estado;
    private String timestampCreacion;
    private String timestampCheckin;
    private boolean confirmedCheckin;

    public ReservaRequest(String idClase, String idUsuario) {
        this.idClase = idClase;
        this.idUsuario = idUsuario;
        this.estado = "CONFIRMADA";
        this.timestampCreacion = java.time.Instant.now().toString();
        this.timestampCheckin = this.timestampCreacion;
        this.confirmedCheckin = true;
    }

    public String getIdClase() {
        return idClase;
    }

    public void setIdClase(String idClase) {
        this.idClase = idClase;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTimestampCreacion() {
        return timestampCreacion;
    }

    public void setTimestampCreacion(String timestampCreacion) {
        this.timestampCreacion = timestampCreacion;
    }

    public String getTimestampCheckin() {
        return timestampCheckin;
    }

    public void setTimestampCheckin(String timestampCheckin) {
        this.timestampCheckin = timestampCheckin;
    }

    public boolean isConfirmedCheckin() {
        return confirmedCheckin;
    }

    public void setConfirmedCheckin(boolean confirmedCheckin) {
        this.confirmedCheckin = confirmedCheckin;
    }
}
