package edu.uade.ritmofit.historial.Model.ui.Adapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.classes.model.Sede;
import edu.uade.ritmofit.historial.Model.CalificacionDTO;
import edu.uade.ritmofit.historial.Model.Repository.HistorialRepository;
import edu.uade.ritmofit.historial.Model.Repository.RespuestaCallback;
import edu.uade.ritmofit.historial.Model.ReservaDTO;

public class HistorialViewModel extends ViewModel {

    @Inject
    HistorialRepository historialRepository;

    private final MutableLiveData<String> errorLiveData;
    private final MutableLiveData<HistorialReservaAdapterDetail> reservaDetalle;
    private final MutableLiveData<List<HistorialReservaAdapter>> reservasResumen;

    @Inject
    public HistorialViewModel(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
        this.errorLiveData = new MutableLiveData<>();
        this.reservaDetalle = new MutableLiveData<>();
        this.reservasResumen = new MutableLiveData<>();
    }

    public MutableLiveData<HistorialReservaAdapterDetail> getReservaDetalle(String idReserva) {
        if(reservaDetalle.getValue() != null){

        }
        else{
            cargarReservaDetalle(idReserva);
        }
        return reservaDetalle;
    }

    public MutableLiveData<List<HistorialReservaAdapter>> getReservasResumen() {
        if (reservasResumen.getValue() != null) {
        } else {
            cargarReservas();
        }

        return reservasResumen;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void cargarReservas() {
        List<HistorialReservaAdapter> reservas = new ArrayList<>();

        historialRepository.fetchHistorial(new RespuestaCallback<List<ReservaDTO>>() {
            @Override
            public void onSuccess(List<ReservaDTO> data) {
                if (data.isEmpty()) {
                    reservasResumen.postValue(reservas); // Publicar lista vacía si no hay reservas
                    return;
                }

                final int totalReservas = data.size();
                final int[] completedRequests = {0}; // Contador para rastrear respuestas completadas

                for (ReservaDTO reserva : data) {
                    historialRepository.fetchClase(reserva.getIdClase(), new RespuestaCallback<Clase>() {
                        @Override
                        public void onSuccess(Clase clase) {
                            historialRepository.fetchSede(clase.getIdSede(), new RespuestaCallback<Sede>() {
                                @Override
                                public void onSuccess(Sede sede) {
                                    synchronized (reservas) { // Sincronizar para evitar condiciones de carrera
                                        reservas.add(new HistorialReservaAdapter(
                                                reserva.getIdReserva(),
                                                clase.getFecha(),
                                                clase.getDisciplina(),
                                                sede.getNombre()
                                        ));
                                        completedRequests[0]++;
                                        if (completedRequests[0] == totalReservas) {
                                            reservasResumen.postValue(reservas); // Publicar solo cuando todas las reservas estén procesadas
                                        }
                                    }
                                }

                                @Override
                                public void onError(String error) {
                                    errorLiveData.postValue(error);
                                    completedRequests[0]++;
                                    if (completedRequests[0] == totalReservas) {
                                        reservasResumen.postValue(reservas); // Publicar lo que se haya procesado
                                    }
                                }
                            });
                        }

                        @Override
                        public void onError(String error) {
                            errorLiveData.postValue(error);
                            synchronized (reservas) {
                                completedRequests[0]++;
                                if (completedRequests[0] == totalReservas) {
                                    reservasResumen.postValue(reservas); // Publicar lo que se haya procesado
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue(error);
                reservasResumen.postValue(reservas); // Publicar lista vacía en caso de error inicial
            }
        });
    }


    public void cargarReservaDetalle(String idReserva) {
        historialRepository.fetchReserva(idReserva, new RespuestaCallback<ReservaDTO>() {
            HistorialReservaAdapterDetail resultado =new HistorialReservaAdapterDetail();
            @Override
            public void onSuccess(ReservaDTO reserva) {
                resultado.setIdReserva(reserva.getIdReserva());
                resultado.setEstado(reserva.getEstado());
                resultado.setTimestampCreacion(reserva.getTimestampCreacion().toString());
                resultado.setTimestampCheckin(reserva.getTimestampCheckin().toString());
                resultado.setConfirmedCheckin(reserva.isConfirmedCheckin());

                // idReserva , idClase,estado, timestampCreacion, timestampCheckin, confirmedCheckin
                historialRepository.fetchClase(reserva.getIdClase(), new RespuestaCallback<Clase>() {

                    @Override
                    public void onSuccess(Clase clase) {
                        resultado.setFechaClase(clase.getFecha());
                        resultado.setFechaClaseDuracion(clase.getFecha());
                        resultado.setDisciplina(clase.getDisciplina());
                        //fechaClase.fechaClaseDuracion.disciplina
                        historialRepository.fetchSede(clase.getIdSede(), new RespuestaCallback<Sede>() {
                            @Override
                            public void onSuccess(Sede sede) {
                                resultado.setSedeNombre(sede.getNombre());
                           //sedeNombre
                            }
                            @Override
                            public void onError(String error) {
                                errorLiveData.postValue(error);
                            }
                        });
                        historialRepository.fetchProfesor(clase.getIdProfesor(), new RespuestaCallback<Profesor>() {
                            @Override
                            public void onSuccess(Profesor profesor) {
                                resultado.setProfesorNombre(profesor.getNombre());
                                //profesorNombre
                            }
                            @Override
                            public void onError(String error) {
                                errorLiveData.postValue(error);
                            }
                        });
                        List<CalificacionDTO> calificaciones = new ArrayList<>();

                        for (String calificacionId : clase.getCalificaciones()) {
                            historialRepository.fetchCalificacion(calificacionId, new RespuestaCallback<CalificacionDTO>() {
                                @Override
                                public void onSuccess(CalificacionDTO calificacion) {
                                    calificaciones.add(calificacion);


                                }
                                @Override
                                public void onError(String error) {
                                    errorLiveData.postValue(error);
                                }
                            });

                        }
                        resultado.setCalificaciones(calificaciones);

                    }
                    @Override
                    public void onError(String error) {
                        errorLiveData.postValue(error);
                    }
                });
                reservaDetalle.postValue(resultado);
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue(error);
            }


        });
    }

}