package edu.uade.ritmofit.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.uade.ritmofit.Modules.InterfaceService;
import edu.uade.ritmofit.data.api.model.SedeResponse;
import retrofit2.Call;

@Singleton
public class SedeRetrofitRepository implements SedeRepository {
    private final InterfaceService apiService;

    @Inject
    public SedeRetrofitRepository(InterfaceService service) {
        this.apiService = service;
    }

    public Call<List<SedeResponse>> getAllSedes() {
        return apiService.getSedes();
    }
    @Override
    public Call<SedeResponse> getSedeById(String id) {
        return apiService.getSedeById(id);
    }
}