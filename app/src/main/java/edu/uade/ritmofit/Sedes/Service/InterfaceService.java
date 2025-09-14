package edu.uade.ritmofit.Sedes.Service;

import java.util.List;

import edu.uade.ritmofit.Sedes.Model.SedeDto;
import edu.uade.ritmofit.Sedes.Model.SedeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceService {
    @GET("sedes")
    Call<List<SedeResponse>> getAllSedes();

    @GET("sedes/{id}")
    Call<SedeResponse> getSedeById(@Path("id") String id);
}