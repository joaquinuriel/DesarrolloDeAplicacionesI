package edu.uade.ritmofit.auth;

import android.util.Log;
import java.io.IOException;
import javax.inject.Inject;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    @Inject
    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = tokenManager.getAccessToken();
        Log.d("AuthInterceptor", "Token enviado: " + (token != null ? token : "null"));

        if (token != null) {
            Request modifiedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(modifiedRequest);
        }
        return chain.proceed(originalRequest);
    }
}