package edu.uade.ritmofit.auth.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import dagger.hilt.android.qualifiers.ApplicationContext;

import javax.inject.Inject;

import edu.uade.ritmofit.auth.model.LoginRequest;
import edu.uade.ritmofit.auth.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_TOKEN_EXPIRATION = "token_expiration";
    private static final long TOKEN_VALIDITY_DURATION_MS = 50 * 60 * 1000; // 50 minutos

    private final AuthApiService apiService;
    private final SharedPreferences sharedPreferences;

    public interface LoginCallback {
        void onSuccess(LoginResponse loginResponse);
        void onFailure(String errorMessage);
    }

    @Inject
    public AuthRepository(@ApplicationContext Context context, AuthApiService apiService) {
        this.apiService = apiService;
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            this.sharedPreferences = EncryptedSharedPreferences.create(
                    PREFS_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            throw new RuntimeException("Error initializing EncryptedSharedPreferences", e);
        }
    }

    public void login(String username, String password, LoginCallback callback) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    long expirationTime = System.currentTimeMillis() + TOKEN_VALIDITY_DURATION_MS;
                    Log.d("AuthRepository", "Nuevo token recibido: " + loginResponse.getAccessToken());
                    sharedPreferences.edit()
                            .putString(KEY_ACCESS_TOKEN, loginResponse.getAccessToken())
                            .putLong(KEY_TOKEN_EXPIRATION, expirationTime)
                            .apply();
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
        String token = sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
        long expirationTime = sharedPreferences.getLong(KEY_TOKEN_EXPIRATION, 0);

        if (token != null && System.currentTimeMillis() < expirationTime) {
            return token;
        } else {
            clearAccessToken();
            return null;
        }
    }

    public void clearAccessToken() {
        Log.d("AuthRepository", "Limpiando token almacenado");
        sharedPreferences.edit()
                .remove(KEY_ACCESS_TOKEN)
                .remove(KEY_TOKEN_EXPIRATION)
                .apply();
    }

    public boolean hasValidToken() {
        String token = sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
        long expirationTime = sharedPreferences.getLong(KEY_TOKEN_EXPIRATION, 0);
        return token != null && System.currentTimeMillis() < expirationTime;
    }
}