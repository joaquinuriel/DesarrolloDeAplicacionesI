package edu.uade.ritmofit.historial.Model.Service;

import java.util.List;

import javax.inject.Inject;

import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.classes.model.Sede;
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

    public Call<ReservaDTO> getReserva(String id) {
        return interfaceHistorialService.getReserva(id);
    }


    public Call<Clase> getClase(String id) {
        return interfaceHistorialService.getClase(id);
    }


    public Call<Profesor> getProfesor(String id) {
        return interfaceHistorialService.getProfesor(id);
    }


    public Call<CalificacionDTO> getCalificacion(String id) {
        return interfaceHistorialService.getCalificacion(id);
    }

    public Call<Sede> getSede(String id) {
        return interfaceHistorialService.getSede(id);
    }
}
