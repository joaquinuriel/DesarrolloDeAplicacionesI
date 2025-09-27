package edu.uade.ritmofit.profile.data.repository;

import edu.uade.ritmofit.profile.data.model.UserProfile;
import retrofit2.Call;

public interface ProfileRepository {
    Call<UserProfile> getUserById(String id);

    Call<UserProfile> updateUser(String id, UserProfile updateUser);
}
