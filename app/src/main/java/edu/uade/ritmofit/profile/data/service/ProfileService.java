package edu.uade.ritmofit.profile.data.service;

import edu.uade.ritmofit.profile.data.model.UserProfile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ProfileService {
    @GET("users/{id}")
    Call<UserProfile> getUserById(@Path("id") String id);

    @PATCH("users/{id}")
    Call<UserProfile> updateUser(@Path("id") String id, @Body UserProfile updatedUser);
}
