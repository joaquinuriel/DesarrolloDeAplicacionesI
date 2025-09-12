package edu.uade.ritmofit.home.service;

import java.util.List;

import edu.uade.ritmofit.home.model.Novedad;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NovedadesHome {

    @GET("api/novedades")
    Call<List<Novedad>> getNovedades();
}
