package edu.uade.ritmofit.historial.Service;

import java.util.List;

import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.classes.model.Sede;
import edu.uade.ritmofit.historial.Model.CalificacionDTO;
import edu.uade.ritmofit.historial.Model.ReservaDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceHistorialService {
    @GET("reservas/usuario/{id}")
    Call<List<ReservaDTO>> getHistorial(@Path("id") String id);

    @GET("reservas/{id}")
    Call<ReservaDTO> getReserva(@Path("id") String id);

    @GET("clases/{id}")
    Call<Clase> getClase(@Path("id") String id);

    @GET("profesores/{id}")
    Call<Profesor> getProfesor(@Path("id") String id);

    @GET("calificaciones/{id}")
    Call<CalificacionDTO> getCalificacion(@Path("id") String id);

    @GET("sedes/{id}")
    Call<Sede> getSede(@Path("id") String id);
}
