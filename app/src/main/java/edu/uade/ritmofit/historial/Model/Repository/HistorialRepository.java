package edu.uade.ritmofit.historial.Model.Repository;


import java.util.List;

import edu.uade.ritmofit.classes.model.Sede;

import edu.uade.ritmofit.classes.model.Clase;

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

    public void fetchHistorial(RespuestaCallback<List<ReservaDTO>> respuesta) {
        if (authRepository == null || authRepository.getPerfilId() == null) {
            throw new IllegalStateException("Perfil no disponible");
        }

        historialService.getHistorial(authRepository.getPerfilId()).enqueue(new Callback<List<ReservaDTO>>() {
            @Override
            public void onResponse(Call<List<ReservaDTO>> call, Response<List<ReservaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    respuesta.onSuccess(response.body());
                } else {
                    String errorMsg = "Error en la respuesta: " + response.code() + " - " + response.message();
                    respuesta.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<ReservaDTO>> call, Throwable t) {
                respuesta.onError("Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

    public void fetchClase(String idClase, RespuestaCallback<Clase> respuesta) {
        historialService.getClase(idClase).enqueue(new Callback<Clase>() {
            @Override
            public void onResponse(Call<Clase> call, Response<Clase> response) {
                if (response.isSuccessful() && response.body() != null) {
                    respuesta.onSuccess(response.body());
                } else {
                    String errorMsg = "Error en la respuesta: " + response.code() + " - " + response.message();
                    respuesta.onError(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<Clase> call, Throwable t) {
                    respuesta.onError("Fallo en la solicitud: " + t.getMessage());

                    }
                });
        }


    public void fetchProfesor(String idProfesor, RespuestaCallback <Profesor> respuesta) {

        historialService.getProfesor(idProfesor).enqueue(new Callback<Profesor>() {

            @Override
            public void onResponse(Call<Profesor> call, Response<Profesor> response) {

                if (response.isSuccessful() && response.body() != null) {
                    respuesta.onSuccess(response.body());
                } else {
                    String errorMsg = "Error en la respuesta: " + response.code() + " - " + response.message();
                    respuesta.onError(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<Profesor> call, Throwable t) {
                respuesta.onError("Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

    public void fetchCalificacion(String idCalificacion, RespuestaCallback <CalificacionDTO> respuesta) {
        historialService.getCalificacion(idCalificacion).enqueue(new Callback<CalificacionDTO>() {
            @Override
            public void onResponse(Call<CalificacionDTO> call, Response<CalificacionDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    respuesta.onSuccess(response.body());
                } else {
                    String errorMsg = "Error en la respuesta: " + response.code() + " - " + response.message();
                    respuesta.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<CalificacionDTO> call, Throwable t) {
                respuesta.onError("Fallo en la solicitud: " + t.getMessage());
            }

        });
    }

    public void fetchSede(String idSede, RespuestaCallback <Sede> respuesta){
        historialService.getSede(idSede).enqueue(new Callback<Sede>() {

            @Override
            public void onResponse(Call<Sede> call, Response<Sede> response) {
                if (response.isSuccessful() && response.body() != null) {
                    respuesta.onSuccess(response.body());
                } else {
                    String errorMsg = "Error en la respuesta: " + response.code() + " - " + response.message();
                    respuesta.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<Sede> call, Throwable t) {
                respuesta.onError("Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

    public void fetchReserva(String idReserva, RespuestaCallback <ReservaDTO> respuesta){
        historialService.getReserva(idReserva).enqueue(new Callback<ReservaDTO>() {
            @Override
            public void onResponse(Call<ReservaDTO> call, Response<ReservaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    respuesta.onSuccess(response.body());
                } else {
                    String errorMsg = "Error en la respuesta: " + response.code() + " - " + response.message();
                    respuesta.onError(errorMsg);
                }
            }
                @Override
            public void onFailure(Call<ReservaDTO> call, Throwable t) {
                respuesta.onError("Fallo en la solicitud: " + t.getMessage());
            }
        });
    }
}









