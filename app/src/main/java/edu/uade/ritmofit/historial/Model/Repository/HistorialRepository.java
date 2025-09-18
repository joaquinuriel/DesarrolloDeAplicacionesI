package edu.uade.ritmofit.historial.Model.Repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import edu.uade.ritmofit.classes.model.Class;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.uade.ritmofit.auth.repository.AuthRepository;
import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.historial.Model.CalificacionDTO;
import edu.uade.ritmofit.historial.Model.ReservaDTO;
import edu.uade.ritmofit.historial.Model.Service.HistorialService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class HistorialRepository {

    private final HistorialService historialService;


    @Inject
    AuthRepository authRepository;

    @Inject
    public HistorialRepository(HistorialService historialService) {
        this.historialService = historialService;
    }
    public Call<List<ReservaDTO>> getHistorial() {
        if (authRepository == null || authRepository.getPerfilId() == null) {
            throw new IllegalStateException("Perfil no disponible");
        }
        return historialService.getHistorial(authRepository.getPerfilId());
    }

    public Call<List<Class>> getClase(String idClase) {
        return historialService.getClase(idClase);

    }

    public Call<List<Profesor>> getProfesor(String idProfesor) {
        return historialService.getProfesor(idProfesor);
    }

    public Call<List<CalificacionDTO>> getCalificacion(String idCalificacion) {
        return historialService.getCalificacion(idCalificacion);
    }
}
