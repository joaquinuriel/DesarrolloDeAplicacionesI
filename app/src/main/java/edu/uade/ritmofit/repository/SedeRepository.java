package edu.uade.ritmofit.repository;

import java.util.List;

import edu.uade.ritmofit.data.api.model.SedeResponse;
import retrofit2.Call;

public interface SedeRepository {
    Call<List<SedeResponse>> getAllSedes();
}
