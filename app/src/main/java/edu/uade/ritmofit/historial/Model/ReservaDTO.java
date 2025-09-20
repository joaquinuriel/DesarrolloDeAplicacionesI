package edu.uade.ritmofit.historial.Model;

public class ReservaDTO {

    private String idReserva;
    private String idClase;
    private String idUsuario;
    private String estado;
    private String timestampCreacion;
    private String timestampCheckin;
    private boolean confirmedCheckin;

    public ReservaDTO ( String idReserva, String idClase, String idUsuario, String estado, String timestampCreacion, String timestampCheckin, boolean confirmedCheckin) {

        this.idReserva = idReserva;
        this.idClase = idClase;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.timestampCreacion = timestampCreacion;
        this.timestampCheckin = timestampCheckin;
        this.confirmedCheckin = confirmedCheckin;

    }
    // Getters
    public String getId() {
        return idReserva;
    }

    public String getIdClase() {
        return idClase;
    }

    public String getIdUsuario() {
        return idUsuario;
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

}
