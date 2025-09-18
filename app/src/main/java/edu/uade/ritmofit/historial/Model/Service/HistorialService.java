package edu.uade.ritmofit.historial.Model.Service;

import java.util.List;

import javax.inject.Inject;

import edu.uade.ritmofit.classes.model.Class;
import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.historial.Model.CalificacionDTO;
import edu.uade.ritmofit.historial.Model.ReservaDTO;
import retrofit2.Call;

public class HistorialService  {

    private final InterfaceHistorialService interfaceHistorialService;

    @Inject
    public HistorialService(InterfaceHistorialService interfaceHistorialService) {
        this.interfaceHistorialService = interfaceHistorialService;
    }


    public Call<List<ReservaDTO>> getHistorial(String id) {
        return interfaceHistorialService.getHistorial(id);
    }


    public Call<List<Class>> getClase(String id) {
        return interfaceHistorialService.getClase(id);
    }


    public Call<List<Profesor>> getProfesor(String id) {
        return interfaceHistorialService.getProfesor(id);
    }


    public Call<List<CalificacionDTO>> getCalificacion(String id) {
        return interfaceHistorialService.getCalificacion(id);
    }
}
