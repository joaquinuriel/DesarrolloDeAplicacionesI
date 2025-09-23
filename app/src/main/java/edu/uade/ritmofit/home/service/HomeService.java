package edu.uade.ritmofit.home.service;

import java.util.List;

import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.classes.model.Sede;
import edu.uade.ritmofit.historial.Model.ReservaDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface HomeService {
    @GET("reservas/usuario/{user_id}/estado")
    Call<List<ReservaDTO>> getReservasActivas(@Path("user_id") String userId,@Query("estado") String estado );

    @GET("sedes/{id}")
    Call<Sede> getSedeById(@Path("id") String sedeId);

    @GET("clases/{id}")
    Call<Clase> getClaseById(@Path("id") String claseId);
}
