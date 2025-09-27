package edu.uade.ritmofit.classes.repository;

import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.classes.service.ProfesorApiService;
import retrofit2.Call;

public class ProfesorRepository {
    private final ProfesorApiService profesorApiService;

    public ProfesorRepository(ProfesorApiService profesorApiService) {
        this.profesorApiService = profesorApiService;
    }

    public Call<Profesor> getProfesorById(String id) {
        return profesorApiService.getProfesorById(id);
    }
}
