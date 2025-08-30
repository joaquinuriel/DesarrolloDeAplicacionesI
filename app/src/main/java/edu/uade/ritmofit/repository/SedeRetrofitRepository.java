package edu.uade.ritmofit.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.uade.ritmofit.Modules.InterfaceService;
import edu.uade.ritmofit.data.api.model.SedeResponse;
import retrofit2.Call;

@Singleton
public class SedeRetrofitRepository implements SedeRepository {
    private final InterfaceService sedesService;

    @Inject
    public SedeRetrofitRepository(InterfaceService service) {
        this.sedesService = service;
    }

    public Call<List<SedeResponse>> getAllSedes() {
        return sedesService.getSedes();
    }
}