package edu.uade.ritmofit.Sedes.Repository;

import java.util.List;

import edu.uade.ritmofit.Sedes.Model.SedeDto;
import retrofit2.Call;

public interface SedeRepository {
    Call<List<SedeDto>> getAllSedes();
    Call<SedeDto> getSedeById(String id);
}