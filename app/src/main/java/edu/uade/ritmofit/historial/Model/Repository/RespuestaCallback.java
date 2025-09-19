package edu.uade.ritmofit.historial.Model.Repository;

public interface RespuestaCallback <T> {
    void onSuccess(T data);
    void onError(String error);
}
