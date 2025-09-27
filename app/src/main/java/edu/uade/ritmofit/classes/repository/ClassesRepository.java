package edu.uade.ritmofit.classes.repository;

import java.util.List;

import javax.inject.Inject;

import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.classes.service.ClassApiService;
import retrofit2.Call;
import retrofit2.http.Path;

public class ClassesRepository implements InterfaceRepositoryClasses {
    private final ClassApiService apiService;

    @Inject
    public ClassesRepository(ClassApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Call<List<Clase>> getClases() {
        return apiService.getClases();
    }

    @Override
    public Call<Clase> getClassById(String id) {
        return apiService.getClassById(id);
    }
}
