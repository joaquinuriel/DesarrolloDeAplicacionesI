package edu.uade.ritmofit.Sedes.n.Repository;

import java.util.List;

import edu.uade.ritmofit.Sedes.n.model.SedeResponse;
import retrofit2.Call;

public interface SedeRepository {
    Call<List<SedeResponse>> getAllSedes();
    Call<SedeResponse> getSedeById(String id);
}
