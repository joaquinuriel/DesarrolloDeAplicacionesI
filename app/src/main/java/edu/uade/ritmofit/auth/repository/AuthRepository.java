package edu.uade.ritmofit.auth.repository;

import android.util.Log;
import javax.inject.Inject;
import edu.uade.ritmofit.auth.TokenManager;
import edu.uade.ritmofit.auth.model.LoginRequest;
import edu.uade.ritmofit.auth.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final AuthApiService apiService;
    private final TokenManager tokenManager;

    public interface LoginCallback {
        void onSuccess(LoginResponse loginResponse);
        void onFailure(String errorMessage);
    }

    @Inject
    public AuthRepository(AuthApiService apiService, TokenManager tokenManager) {
        this.apiService = apiService;
        this.tokenManager = tokenManager;
    }

    public void login(String username, String password, LoginCallback callback) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Log.d("AuthRepository", "Nuevo token recibido: " + loginResponse.getAccessToken());
                    tokenManager.saveToken(loginResponse.getAccessToken());
                    callback.onSuccess(loginResponse);
                } else {
                    callback.onFailure("Login failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onFailure("Login failed: " + t.getMessage());
                Log.e("Auth", "Login failed: " + t.getMessage());
            }
        });
    }

    public String getAccessToken() {
        return tokenManager.getAccessToken();
    }

    public void clearAccessToken() {
        tokenManager.clearAccessToken();
    }

    public boolean hasValidToken() {
        return tokenManager.hasValidToken();
    }
}