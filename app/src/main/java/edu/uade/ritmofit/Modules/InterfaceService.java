package edu.uade.ritmofit.Modules;

import java.util.List;

import edu.uade.ritmofit.data.api.model.SedeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceService {

    @GET("sedes")
    Call<List<SedeResponse>> getSedes();

    @GET("sedes/{id}")
    Call<SedeResponse> getSedeById(@Path("id") String id);
}
