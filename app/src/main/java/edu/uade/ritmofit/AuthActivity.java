package edu.uade.ritmofit;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.core.content.ContextCompat;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.EncryptedSharedPreferences;

import java.util.concurrent.Executor;

import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricManager;

public class AuthActivity extends AppCompatActivity {
    private Context context;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null) {
            showLogin();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                BiometricAuthenticator(this, new AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSuccess() {
                        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(int errorCode, String errorMessage) {
                        Log.e("Auth", "Login failed: " + errorMessage);
                    }
                });

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    authenticate();
                }
            }
        }
    }

    public void showLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }

    public void showSignUp() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SignUpFragment())
                .addToBackStack(null) // allows going back to login with back button
                .commit();
    }

    public void confirmLogin() {
        EditText usernameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        AuthResult result = task.getResult();
                        FirebaseUser user = result.getUser();

                        // guardar el token

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Exception e = task.getException();
                        Log.e("Auth", "Login failed", e);
                        Toast.makeText(AuthActivity.this,
                                "Login failed: " + (e != null ? e.getMessage() : "Unknown error"),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void confirmSignUp() {
        EditText usernameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl("https://desarrollo-i.web.app/__/auth/action")
                .setHandleCodeInApp(true)
                .setAndroidPackageName("edu.uade.ritmofit", true, "12")
                .build();

        FirebaseAuth.getInstance()
                .sendSignInLinkToEmail(username, actionCodeSettings)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent: " + username);
                        Toast.makeText(AuthActivity.this, "Email sent", Toast.LENGTH_SHORT).show();

                        MasterKey masterKey = null;
                        try {
                            masterKey = new MasterKey.Builder(AuthActivity.this)
                                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                                    .build();

                            SharedPreferences securePrefs = EncryptedSharedPreferences.create(
                                    AuthActivity.this,
                                    "auth_prefs",
                                    masterKey,
                                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                            );

                            securePrefs.edit().putString("emailForSignIn", username).apply();
                            securePrefs.edit().putString("passwordToLink", password).apply();
                        } catch (Exception e) {
                            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                            prefs.edit().putString("emailForSignIn", username).apply();
                            prefs.edit().putString("passwordToLink", password).apply();
                        }
                    } else {
                        Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        Log.e("Auth", task.getException().getMessage());
                    }
                });
    }

    public interface AuthenticationCallback {
        void onAuthenticationSuccess();
        void onAuthenticationError(int errorCode, String errorMessage);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void BiometricAuthenticator(AppCompatActivity activity, AuthenticationCallback callback) {
        this.context = activity;
        this.executor = ContextCompat.getMainExecutor(activity);

        biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e(TAG, "Authentication error: " + errString);
                callback.onAuthenticationError(errorCode, errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.d(TAG, "Authentication succeeded!");
                callback.onAuthenticationSuccess();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e(TAG, "Authentication failed");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación")
                .setSubtitle("Inicie sesión usando su huella digital o credencial del dispositivo")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG |
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void authenticate() {
        BiometricManager biometricManager = BiometricManager.from(context);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG |
                BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(TAG, "App can authenticate using biometrics.");
                biometricPrompt.authenticate(promptInfo);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e(TAG, "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e(TAG, "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.e(TAG, "The user hasn't associated any biometric credentials with their account.");
                // Prompt the user to set up a lock screen credential
                final Intent enrollIntent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                context.startActivity(enrollIntent);
                break;
            default:
                Log.e(TAG, "Unknown error.");
                break;
        }
    }
}
