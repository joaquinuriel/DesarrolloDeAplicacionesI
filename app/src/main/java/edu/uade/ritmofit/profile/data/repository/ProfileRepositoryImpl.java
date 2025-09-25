package edu.uade.ritmofit.profile.data.repository;

import java.util.List;

import javax.inject.Inject;

import edu.uade.ritmofit.profile.data.model.UserProfile;
import edu.uade.ritmofit.profile.data.service.ProfileService;
import retrofit2.Call;

public class ProfileRepositoryImpl implements ProfileRepository{
    private final ProfileService service;

    @Inject
    public ProfileRepositoryImpl(ProfileService service) {
        this.service = service;
    }

    @Override
    public Call<UserProfile> getUserById(String id) {
        return service.getUserById(id);
    }

    @Override
    public Call<List<UserProfile>> getAllUsers() {
        return service.getAllUsers();
    }
}
