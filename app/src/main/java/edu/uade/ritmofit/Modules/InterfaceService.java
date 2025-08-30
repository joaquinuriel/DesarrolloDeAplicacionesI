package edu.uade.ritmofit.Modules;

import java.util.List;

import edu.uade.ritmofit.data.api.model.SedeResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceService {

    @GET("sedes")
    Call<List<SedeResponse>> getSedes();
}
