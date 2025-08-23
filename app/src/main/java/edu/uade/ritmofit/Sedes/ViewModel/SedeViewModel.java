package edu.uade.ritmofit.Sedes.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.uade.ritmofit.Sedes.Model.SedeDto;
import edu.uade.ritmofit.Sedes.Service.SedeService;
import edu.uade.ritmofit.conecciones.ApiService;

import java.util.List;

public class SedeViewModel extends ViewModel {
    private final MutableLiveData<List<SedeDto>> sedesLiveData = new MutableLiveData<>();
    private final SedeService sedeService;

    public SedeViewModel() {
        this.sedeService = new SedeService();
    }

    public LiveData<List<SedeDto>> getSedes() {
        return sedesLiveData;
    }

    public void fetchSedes() {
        sedeService.fetchSedes(new ApiService.OnSedeFetchListener() {
            @Override
            public void onSedeFetched(List<SedeDto> sedes) {
                sedesLiveData.postValue(sedes);
            }

            @Override
            public void onSedeFetchError(Throwable t) {
                sedesLiveData.postValue(null); // O maneja el error como prefieras
            }
        });
    }
}