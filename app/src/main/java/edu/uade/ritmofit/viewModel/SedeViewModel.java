package edu.uade.ritmofit.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.uade.ritmofit.data.api.model.SedeResponse;
import edu.uade.ritmofit.repository.SedeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class SedeViewModel extends ViewModel {
    private final SedeRepository repository;
    private final MutableLiveData<List<SedeResponse>> sedesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<SedeResponse> sedeDetailLiveData = new MutableLiveData<>();

    @Inject
    public SedeViewModel(SedeRepository repository) {
        this.repository = repository;
        fetchSedes();
    }

    public LiveData<List<SedeResponse>> getSedes() {
        return sedesLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public LiveData<SedeResponse> getSedeDetail() {
        return sedeDetailLiveData;
    }

    private void fetchSedes() {
        Call<List<SedeResponse>> call = repository.getAllSedes();
        call.enqueue(new Callback<List<SedeResponse>>() {
            @Override
            public void onResponse(Call<List<SedeResponse>> call, Response<List<SedeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sedesLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<SedeResponse>> call, Throwable t) {
                errorLiveData.setValue("Error: " + t.getMessage());
                Log.e("SedeViewModel", "Fallo en la solicitud", t);
            }
        });
    }

    public void fetchSedeDetail(String id) {
        Call<SedeResponse> call = repository.getSedeById(id);
        call.enqueue(new Callback<SedeResponse>() {
            @Override
            public void onResponse(Call<SedeResponse> call, Response<SedeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sedeDetailLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SedeResponse> call, Throwable t) {
                errorLiveData.setValue("Error: " + t.getMessage());
                Log.e("SedeViewModel", "Fallo al obtener detalles", t);
            }
        });
    }
}