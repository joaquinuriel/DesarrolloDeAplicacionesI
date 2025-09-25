package edu.uade.ritmofit.classes.service;

import java.util.List;

import edu.uade.ritmofit.classes.model.Clase;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ClassApiService {
    @GET("clases")
    Call<List<Clase>> getClasses();
    @GET("clases/{id}")
    Call<Clase> getClassById(@Path("id") String id);
}