package edu.uade.ritmofit.home.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.uade.ritmofit.auth.TokenManager;
import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.classes.model.Sede;
import edu.uade.ritmofit.historial.Model.ReservaDTO;
import edu.uade.ritmofit.home.service.HomeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<String>> reservasLiveData = new MutableLiveData<>();
    private final HomeService homeService;
    private final TokenManager tokenManager;

    private final String estado = "CONFIRMADA";

    @Inject
    public HomeViewModel(HomeService homeService, TokenManager tokenManager) {
        this.homeService = homeService;
        this.tokenManager = tokenManager;
    }

    public LiveData<List<String>> getReservas() {
        return reservasLiveData;
    }

    public void cargarReservas() {
        String userId = tokenManager.getUserId();
        homeService.getReservasActivas(userId, estado)
                .enqueue(new Callback<List<ReservaDTO>>() {
                    @Override
                    public void onResponse(Call<List<ReservaDTO>> call, Response<List<ReservaDTO>> response) {
                        if (response.isSuccessful()) {
                            //reservasLiveData.setValue(response.body());
                            procesarReserva(response.body());
                        } else {
                            reservasLiveData.setValue(new ArrayList<>());
                            Log.e("HomeViewModel", "Error en la respuesta: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ReservaDTO>> call, Throwable t) {
                        reservasLiveData.setValue(new ArrayList<>());
                        Log.e("HomeViewModel", "Error al obtener las reservas: " + t.getMessage());
                    }
                });
    }

    private void procesarReserva(List<ReservaDTO> reservas) {
        List<String> resultados = new ArrayList<>();
        for (ReservaDTO reserva : reservas) {
            homeService.getClaseById(reserva.getIdClase())
                    .enqueue(new Callback<Clase>(){
                        @Override
                        public void onResponse(Call<Clase> call, Response<Clase> response) {
                            if (response.isSuccessful()) {
                                Clase clase = response.body();
                                homeService.getSedeById(clase.getIdSede())
                                        .enqueue(new Callback<Sede>() {
                                            @Override
                                            public void onResponse(Call<Sede> call, Response<Sede> response) {
                                                if (response.isSuccessful()) {
                                                    Sede sede = response.body();
                                                    String resultado = "Clase: " + clase.getDisciplina() +
                                                            ", Sede: " + sede.getNombre() +
                                                            ", Fecha: " + clase.getFecha()
                                                            + ", Horario: " + clase.getHorarioInicio() + " - " + clase.getHorarioFin();
                                                    resultados.add(resultado);
                                                    reservasLiveData.setValue(resultados);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Sede> call, Throwable throwable) {

                                            }
                                        });
                            }
                        }
                        @Override
                        public void onFailure(Call<Clase> call, Throwable throwable) {
                            Log.e("HomeViewModel", "Error al obtener la clase: " + throwable.getMessage());
                        }
                    });
        }


    }
}
