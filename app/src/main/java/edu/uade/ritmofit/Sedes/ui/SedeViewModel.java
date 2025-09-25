package edu.uade.ritmofit.Sedes.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.uade.ritmofit.Sedes.Model.SedeDto;
import edu.uade.ritmofit.Sedes.Repository.SedeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class SedeViewModel extends ViewModel {

    private final SedeRepository sedeRepository;
    private final MutableLiveData<List<SedeDto>> sedesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<SedeDto> sedeDetailLiveData = new MutableLiveData<>();

    @Inject
    public SedeViewModel(SedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
        fetchSedes();
    }

    public LiveData<List<SedeDto>> getSedes() {
        return sedesLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public LiveData<SedeDto> getSedeDetail() {
        return sedeDetailLiveData;
    }

    public void fetchSedes() {
        sedeRepository.getAllSedes().enqueue(new Callback<List<SedeDto>>() {
            @Override
            public void onResponse(Call<List<SedeDto>> call, Response<List<SedeDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sedesLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<SedeDto>> call, Throwable t) {
                errorLiveData.setValue("Error: " + t.getMessage());
            }
        });
    }

    public void fetchSedeDetail(String id) {
        sedeRepository.getSedeById(id).enqueue(new Callback<SedeDto>() {
            @Override
            public void onResponse(Call<SedeDto> call, Response<SedeDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sedeDetailLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SedeDto> call, Throwable t) {
                errorLiveData.setValue("Error: " + t.getMessage());
            }
        });
    }
}