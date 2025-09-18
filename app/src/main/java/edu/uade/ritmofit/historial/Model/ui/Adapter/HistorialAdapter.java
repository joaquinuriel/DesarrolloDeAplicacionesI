package edu.uade.ritmofit.historial.Model.ui.Adapter;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import edu.uade.ritmofit.historial.Model.Repository.HistorialRepository;
import edu.uade.ritmofit.historial.Model.ReservaDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialAdapter {
    @Inject
    private HistorialRepository historialRepository;

    public HistorialAdapter(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
    }
    private MutableLiveData<String> errorLiveData;
    private MutableLiveData<HistorialReservaAdapterDetail> reservaDetalle;
    private MutableLiveData<List<HistorialReservaAdapter>>  reservasResumen;

    public MutableLiveData<HistorialReservaAdapterDetail> getReservasDetalle() {
        return reservaDetalle;
    }
    public MutableLiveData<List<HistorialReservaAdapter>> getReservasResumen() {
        return reservasResumen;
    }

    public void fetchReservas(){
        try {


            Call<List<ReservaDTO>> call = historialRepository.getHistorial();
            call.enqueue(new Callback<List<ReservaDTO>>() {
                @Override
                public void onResponse(Call<List<ReservaDTO>> call, Response<List<ReservaDTO>> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        for (ReservaDTO reservaDTO : response.body()) {

                            String IdReserva = reservaDTO.getIdReserva();
                            String IdClase = reservaDTO.getIdClase();
                            String disciplina;//en clase
                            String sede;// en sede






                        }
                    } else {
                        errorLiveData.setValue("Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<ReservaDTO>> call, Throwable t) {
                    errorLiveData.setValue("Fallo en la solicitud: " + t.getMessage());
                }
            });
        } catch (IllegalStateException e) {
            errorLiveData.setValue(e.getMessage());
        }

    }

}


