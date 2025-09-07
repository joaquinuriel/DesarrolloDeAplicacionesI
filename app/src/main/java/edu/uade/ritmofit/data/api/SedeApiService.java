package edu.uade.ritmofit.data.api;

import edu.uade.ritmofit.data.model.Sede;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SedeApiService {
    @GET("sedes/{id}")
    Call<Sede> getSede(@Path("id") String id);
}