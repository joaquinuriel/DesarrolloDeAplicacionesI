package edu.uade.ritmofit.historial.Model.Service;

import java.util.List;

import edu.uade.ritmofit.classes.model.Class;
import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.historial.Model.CalificacionDTO;
import edu.uade.ritmofit.historial.Model.ReservaDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceHistorialService {

    @GET("reservas/usuario/{id}")
    Call<List<ReservaDTO>> getHistorial(@Path("id") String id);

    @GET("/clases/{id}")
    Call<List<Class>> getClase(@Path("id") String id);

    @GET("/profesores/{id}")
    Call<List<Profesor>> getProfesor(@Path("id") String id);

    @GET("/calificaciones/{id}")
    Call<List<CalificacionDTO>> getCalificacion(@Path("id") String id);
}
