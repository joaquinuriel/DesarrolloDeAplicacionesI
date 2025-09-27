package edu.uade.ritmofit.classes.service;

import java.util.List;

import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.historial.Model.Reserva;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClassApiService {
    @GET("clases")
    Call<List<Clase>> getClases();
    @GET("clases/{id}")
    Call<Clase> getClassById(@Path("id") String id);
    @PUT("/clases/{id}")
    Call<Clase> updateClase(@Path("id") String id, @Body Clase clase);
}