package edu.uade.ritmofit.profile.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.uade.ritmofit.profile.data.model.UserProfile;
import edu.uade.ritmofit.profile.data.repository.ProfileRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private final ProfileRepository profileRepository;
    private final MutableLiveData<UserProfile> userProfile = new MutableLiveData<>();
    private final MutableLiveData<List<UserProfile>> allUsers = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public ProfileViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public LiveData<UserProfile> getUserProfile() {
        return userProfile;
    }

    public LiveData<List<UserProfile>> getAllUsers() {
        return allUsers;
    }

    public LiveData<String> getError() {
        return error;
    }

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

    public void fetchAllUsers() {
        profileRepository.getAllUsers().enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allUsers.postValue(response.body());
                } else {
                    error.postValue("Error al obtener usuarios");
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }
}