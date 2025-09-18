package edu.uade.ritmofit.Sedes.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.uade.ritmofit.Sedes.Model.SedeDto;
import edu.uade.ritmofit.Sedes.Service.ApiService;
import retrofit2.Call;
import java.util.List;

@Singleton
public class SedeRetrofitRepository implements SedeRepository {

    private final ApiService apiService;

    @Inject
    public SedeRetrofitRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Call<List<SedeDto>> getAllSedes() {
        return apiService.getAllSedes();
    }

    @Override
    public Call<SedeDto> getSedeById(String id) {
        return apiService.getSedeById(id);
    }
}