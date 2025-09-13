package edu.uade.ritmofit.classes.service;

import java.util.List;

import edu.uade.ritmofit.classes.model.Class;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ClassApiService {
    @GET("clases")
    Call<List<Class>> getClasses();
    @GET("clases/{id}")
    Call<Class> getClassById(@Path("id") String id);
}