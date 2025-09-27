package edu.uade.ritmofit.classes.service;

import edu.uade.ritmofit.classes.model.Profesor;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfesorApiService {
    @GET("profesores/{id}")
    Call<Profesor> getProfesorById(@Path("id") String id);
}
