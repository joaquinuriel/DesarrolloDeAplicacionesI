package edu.uade.ritmofit.profile.data.service;

import java.util.List;

import edu.uade.ritmofit.profile.data.model.UserProfile;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfileService {
    @GET("users/{id}")
    Call<UserProfile> getUserById(@Path("id") String id);

}
