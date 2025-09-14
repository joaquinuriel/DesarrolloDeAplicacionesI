package edu.uade.ritmofit.auth.repository;

import edu.uade.ritmofit.auth.model.LoginRequest;
import edu.uade.ritmofit.auth.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}