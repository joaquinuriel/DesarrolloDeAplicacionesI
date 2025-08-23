package edu.uade.ritmofit.Sedes.Service;

import edu.uade.ritmofit.Sedes.Model.SedeDto;
import edu.uade.ritmofit.conecciones.ApiService;
import java.util.List;

public class SedeService {
    private final ApiService networkService;

    public SedeService() {
        this.networkService = new ApiService();
    }

    public void fetchSedes(ApiService.OnSedeFetchListener listener) {
        networkService.fetchSedes(listener);
    }
}