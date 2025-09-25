package edu.uade.ritmofit.historial.Repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import edu.uade.ritmofit.auth.TokenManager;
import edu.uade.ritmofit.auth.repository.AuthApiService;
import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.classes.model.Sede;
import edu.uade.ritmofit.historial.Model.Reserva;
import edu.uade.ritmofit.historial.Service.InterfaceHistorialService;
import edu.uade.ritmofit.historial.Model.CalificacionDTO;
import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.historial.Model.ReservaDTO;
import edu.uade.ritmofit.historial.Model.ReservaDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryHistorial implements InterfaceRepositoryHistorial {
    private static final String TAG = "RepositoryHistorial";

    private final InterfaceHistorialService historialService;
    private final TokenManager tokenManager;

    @Inject
    public RepositoryHistorial(InterfaceHistorialService historialService, TokenManager tokenManager ) {
        this.historialService = historialService;
        this.tokenManager = tokenManager;


    }


/**
 * Obtiene la lista de reservas del usuario, incluyendo información de la clase y la sede.
 * @param callback Callback para manejar el resultado de la operación.
 */
@Override
public void getHistorial(ReservasListCallback callback) {
    List<Reserva> reservasList = new ArrayList<>();

    historialService.getHistorial(tokenManager.getUserId()).enqueue(new Callback<List<ReservaDTO>>() {
        @Override
        public void onResponse(Call<List<ReservaDTO>> call, Response<List<ReservaDTO>> response) {
            if (!response.isSuccessful() || response.body() == null) {
                Log.e(TAG, "Error al obtener historial: " + response.code());
                handleErrorReservas("Error al obtener historial", null, callback);

                return;
            }
            List<ReservaDTO> dtoList = response.body();
            if (dtoList.isEmpty()) {
                callback.onSuccess(reservasList); // Retornar lista vacía si no hay reservas
                return;
            }

            AtomicInteger completadas = new AtomicInteger(0);
            int total = dtoList.size();

            for (ReservaDTO dto : dtoList) {

                historialService.getClase(dto.getIdClase()).enqueue(new Callback<Clase>() {
                    @Override
                    public void onResponse(Call<Clase> call, Response<Clase> claseResponse) {
                        if (!claseResponse.isSuccessful() || claseResponse.body() == null) {
                            Log.e(TAG, "Error al obtener una de las clases: " + claseResponse.code());
                            // Continuar con las demás reservas en caso de error
                            if (completadas.incrementAndGet() == total) {
                                callback.onSuccess(reservasList);
                            }
                            return;
                        }

                        Clase clase = claseResponse.body();
                        historialService.getSede(clase.getIdSede()).enqueue(new Callback<Sede>() {
                            @Override
                            public void onResponse(Call<Sede> call, Response<Sede> sedeResponse) {
                                if (!sedeResponse.isSuccessful() || sedeResponse.body() == null) {
                                    Log.e(TAG, "Error al obtener una de las sedes: " + sedeResponse.code());
                                    // Continuar con las demás reservas en caso de error
                                    if (completadas.incrementAndGet() == total) {
                                        callback.onSuccess(reservasList);
                                    }
                                    return;
                                }

                                String sedeNombre = sedeResponse.body().getNombre();
                                Reserva reserva = buildReserva(dto, clase, sedeNombre);
                                reservasList.add(reserva);

                                if (completadas.incrementAndGet() == total) {
                                    callback.onSuccess(reservasList);
                                }
                            }

                            @Override
                            public void onFailure(Call<Sede> call, Throwable t) {
                                Log.e(TAG, "Error al obtener una de las sedes" + t.getMessage());
                                // Continuar con las demás reservas en caso de error
                                if (completadas.incrementAndGet() == total) {
                                    callback.onSuccess(reservasList);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Clase> call, Throwable t) {
                        Log.e(TAG, "Error al obtener una de las clases" + t.getMessage());
                        // Continuar con las demás reservas en caso de error
                        if (completadas.incrementAndGet() == total) {
                            callback.onSuccess(reservasList);
                        }
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<List<ReservaDTO>> call, Throwable t) {
            Log.e(TAG, "Error al obtener el historial" + t.getMessage());
            handleErrorReservas("Error al obtener historial", t, callback);
        }
    });
}

    @Override
    public void getReserva(String id, ReservaDetailCallback callback) {
        historialService.getReserva(id).enqueue(new Callback<ReservaDTO>() {
            @Override
            public void onResponse(Call<ReservaDTO> call, Response<ReservaDTO> reservaResponse) {
                if (!reservaResponse.isSuccessful() || reservaResponse.body() == null) {
                    Log.e(TAG, "Error al obtener  la reserva" + reservaResponse.errorBody());
                    handleError("Error al obtener reserva", null, callback);
                    return;
                }

                ReservaDTO dto = reservaResponse.body();

                // Obtener Clase
                historialService.getClase(dto.getIdClase()).enqueue(new Callback<Clase>() {
                    @Override
                    public void onResponse(Call<Clase> call, Response<Clase> claseResponse) {
                        if (!claseResponse.isSuccessful() || claseResponse.body() == null) {
                            Log.e(TAG, "Error al obtener  la reserva" + claseResponse.errorBody());
                            handleError("Error al obtener clase", null, callback);
                            return;
                        }

                        Clase clase = claseResponse.body();

                        // Obtener Sede
                        historialService.getSede(clase.getIdSede()).enqueue(new Callback<Sede>() {
                            @Override
                            public void onResponse(Call<Sede> call, Response<Sede> sedeResponse) {
                                if (!sedeResponse.isSuccessful() || sedeResponse.body() == null) {
                                    Log.e(TAG, "Error al obtener  la reserva" + sedeResponse.errorBody());
                                    handleError("Error al obtener sede", null, callback);
                                    return;
                                }

                                String sedeNombre = sedeResponse.body().getNombre();

                                // Obtener Profesor
                                historialService.getProfesor(clase.getIdProfesor()).enqueue(new Callback<Profesor>() {
                                    @Override
                                    public void onResponse(Call<Profesor> call, Response<Profesor> profesorResponse) {
                                        if (!profesorResponse.isSuccessful() || profesorResponse.body() == null) {
                                            Log.e(TAG, "Error al obtener  la reserva" + profesorResponse.errorBody());
                                            handleError("Error al obtener profesor", null, callback);
                                            return;
                                        }

                                        String profesorNombre = profesorResponse.body().getNombre();

                                        // Obtener calificaciones
                                        List<String> idsCalificaciones = clase.getCalificaciones();
                                        if (idsCalificaciones == null || idsCalificaciones.isEmpty()) {
                                            // Retornar con lista vacía
                                            ReservaDetail detail = buildReservaDetail(
                                                    dto,
                                                    clase,
                                                    sedeNombre,
                                                    profesorNombre,
                                                    new ArrayList<>()
                                            );
                                            callback.onSuccess(detail);
                                            return;
                                        }

                                        List<CalificacionDTO> calificaciones = new ArrayList<>();
                                        AtomicInteger completadas = new AtomicInteger(0);
                                        int total = idsCalificaciones.size();

                                        for (String idCalificacion : idsCalificaciones) {
                                            historialService.getCalificacion(idCalificacion).enqueue(new Callback<CalificacionDTO>() {
                                                @Override
                                                public void onResponse(Call<CalificacionDTO> call, Response<CalificacionDTO> calificacionResponse) {
                                                    if (calificacionResponse.isSuccessful() && calificacionResponse.body() != null) {
                                                        calificaciones.add(calificacionResponse.body());
                                                    }
                                                    if (completadas.incrementAndGet() == total) {
                                                        ReservaDetail detail = buildReservaDetail(dto, clase, sedeNombre, profesorNombre, calificaciones);
                                                        callback.onSuccess(detail);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<CalificacionDTO> call, Throwable t) {
                                                    Log.e(TAG, "Error al obtener calificación" + t.getMessage());
                                                    handleError("Error al obtener calificación", t, callback);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Profesor> call, Throwable t) {
                                        Log.e(TAG, "Error al obtener profesor" + t.getMessage());
                                        handleError("Error al obtener profesor", t, callback);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Sede> call, Throwable t) {
                                Log.e(TAG, "Error al obtener sede" + t.getMessage());
                                handleError("Error al obtener sede", t, callback);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Clase> call, Throwable t) {
                        Log.e(TAG, "Error al obtener clase" + t.getMessage());
                        handleError("Error al obtener clase", t, callback);
                    }
                });
            }

            @Override
            public void onFailure(Call<ReservaDTO> call, Throwable t) {
                Log.e(TAG, "Error al obtener reserva" + t.getMessage());
                handleError("Error al obtener reserva", t, callback);
            }
        });
    }

    private Reserva buildReserva(ReservaDTO dto, Clase clase, String sedeNombre) {
        return new Reserva(dto.getId(), clase.getDisciplina(), sedeNombre, clase.getFecha());
    }

    // Construcción centralizada de ReservaDetail
    private ReservaDetail buildReservaDetail(ReservaDTO dto, Clase clase, String sedeNombre, String profesorNombre, List<CalificacionDTO> calificaciones) {
        return new ReservaDetail(
                dto.getId(),
                dto.getEstado(),
                String.valueOf(dto.getTimestampCreacion()),
                String.valueOf(dto.getTimestampCheckin()),
                dto.isConfirmedCheckin(),
                clase.getFecha(),
                String.valueOf(clase.getDuracion()),
                clase.getDisciplina(),
                calificaciones,
                sedeNombre,
                profesorNombre
        );
    }





    // Manejo de errores centralizado
    private void handleError(String message, Throwable t, ReservaDetailCallback callback) {
        if (t != null) {
            callback.onError(message + ": " + t.getMessage());
        } else {
            callback.onError(message);
        }
    }

    private void handleErrorReservas(String message, Throwable t, ReservasListCallback callback){
            if (t != null) {
                callback.onError(message + ": " + t.getMessage());
            } else {
                callback.onError(message);

            }
        }
}
