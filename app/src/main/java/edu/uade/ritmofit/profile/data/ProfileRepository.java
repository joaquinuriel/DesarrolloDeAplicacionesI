package edu.uade.ritmofit.profile.data;

import java.util.List;

import edu.uade.ritmofit.profile.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfileRepository {
    @GET("users/{id}")
    Call<User> getUserbyId(@Path("id") String id);

    @GET("users")
    Call<List<User>> getAllUsers();
}
