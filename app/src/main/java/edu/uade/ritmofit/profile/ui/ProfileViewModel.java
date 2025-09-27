package edu.uade.ritmofit.profile.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.uade.ritmofit.profile.data.model.UserProfile;
import edu.uade.ritmofit.profile.data.repository.ProfileRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class ProfileViewModel extends ViewModel {
    private final ProfileRepository profileRepository;
    private final MutableLiveData<UserProfile> userProfile = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<String> info = new MutableLiveData<>();


    @Inject
    public ProfileViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public LiveData<UserProfile> getUserProfile() {
        return userProfile;
    }

    public LiveData<String> getError() {
        return error;
    }
    public LiveData<String> getInfo() { return info; }


    public void fetchUserById(String id) {
        profileRepository.getUserById(id).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(@NonNull Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userProfile.postValue(response.body());
                } else {
                    error.postValue("Error al obtener usuario");
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }

    public void updateUser(String id, UserProfile updatedUser) {
        // Validaciones básicas
        String name = updatedUser.getName();
        String email = updatedUser.getEmail();

        if (name == null || !name.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            error.postValue("El nombre solo puede contener letras");
            return;
        }
        if (email == null || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            error.postValue("El email no tiene un formato válido");
            return;
        }

        profileRepository.updateUser(id, updatedUser).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(@NonNull Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userProfile.postValue(response.body());
                    info.postValue("Perfil actualizado");
                } else {
                    error.postValue("Error al actualizar usuario");
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }

}