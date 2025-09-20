package edu.uade.ritmofit.auth;



import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TokenManager {
    private static final String TAG = "Token manager";
    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TOKEN_EXPIRATION = "token_expiration";
    private static final long TOKEN_VALIDITY_DURATION_MS = 50 * 60 * 1000;

    private final SharedPreferences sharedPreferences;

    @Inject
    public TokenManager(@ApplicationContext Context context) {
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

    public void saveUserId(String userId) {
        sharedPreferences.edit()
                .putString(KEY_USER_ID, userId)
                .apply();
        Log.d(TAG, "id de usuario guardado: " + userId);
    }
    public String getUserId() {
        Log.d(TAG, "id de usuario USADO: " + sharedPreferences.getString(KEY_USER_ID, null));
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public void saveToken(String token) {
        long expirationTime = System.currentTimeMillis() + TOKEN_VALIDITY_DURATION_MS;
        sharedPreferences.edit()
                .putString(KEY_ACCESS_TOKEN, token)
                .putLong(KEY_TOKEN_EXPIRATION, expirationTime)
                .apply();
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