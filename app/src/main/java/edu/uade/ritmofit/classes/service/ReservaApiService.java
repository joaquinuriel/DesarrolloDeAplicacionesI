package edu.uade.ritmofit.classes.service;

import edu.uade.ritmofit.classes.model.ReservaRequest;
import edu.uade.ritmofit.classes.model.Reservas;
import edu.uade.ritmofit.historial.Model.Reserva;
import edu.uade.ritmofit.historial.Model.ReservaDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ReservaApiService {
    @POST("reservas")
    Call<Void> reservarClase(@Body ReservaRequest reservaRequest);

    @GET("reservas")
    Call<List<Reserva>> getReservas();

    @GET("reservas/{id}")
    Call<Reserva> getReservaById(@Path("id") String id);

    @GET("reservas/usuario/{idUsuario}")
    Call<List<ReservaRequest>> getReservasByUsuario(@Path("idUsuario") String idUsuario);
}