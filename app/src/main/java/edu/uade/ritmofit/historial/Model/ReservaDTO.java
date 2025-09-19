package edu.uade.ritmofit.historial.Model;

import java.time.LocalDateTime;

public class ReservaDTO {
    private String idReserva;
    private String idClase;
    private String idUsuario;
    private String estado;
    private LocalDateTime timestampCreacion;
    private LocalDateTime timestampCheckin;
    private boolean confirmedCheckin;
    public ReservaDTO(String idReserva, String idClase, String idUsuario, String estado, LocalDateTime timestampCreacion, LocalDateTime timestampCheckin, boolean confirmedCheckin) {
        this.idReserva = idReserva;
        this.idClase = idClase;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.timestampCreacion = timestampCreacion;
        this.timestampCheckin = timestampCheckin;
        this.confirmedCheckin = confirmedCheckin;

    }
    public String getEstado() {
        return estado;
    }
    public String getIdClase() {
        return idClase;

    }
    public String getIdUsuario() {
        return idUsuario;
    }
    public String getIdReserva() {
        return idReserva;
    }
    public LocalDateTime getTimestampCreacion() {
        return timestampCreacion;
    }
    public LocalDateTime getTimestampCheckin() {
        return timestampCheckin;
    }
    public boolean isConfirmedCheckin() {
        return confirmedCheckin;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }




}
