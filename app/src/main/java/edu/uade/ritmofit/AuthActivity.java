package edu.uade.ritmofit;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.security.crypto.MasterKey;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme;
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AuthActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null) {
            showLogin();
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
                    }
                });
    }
}
