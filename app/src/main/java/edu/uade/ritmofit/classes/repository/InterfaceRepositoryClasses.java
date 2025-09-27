package edu.uade.ritmofit.classes.repository;

import java.util.List;

import edu.uade.ritmofit.classes.model.Clase;
import retrofit2.Call;
import retrofit2.http.Path;

public interface InterfaceRepositoryClasses {
    Call<List<Clase>> getClases();
    Call<Clase> getClassById(@Path("id") String id);
}
