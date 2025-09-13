package edu.uade.ritmofit.Sedes.Repository;

import java.util.List;

import edu.uade.ritmofit.Sedes.Model.SedeResponse;
import retrofit2.Call;

public interface SedeRepository {
    Call<List<SedeResponse>> getAllSedes();
    Call<SedeResponse> getSedeById(String id);
}
