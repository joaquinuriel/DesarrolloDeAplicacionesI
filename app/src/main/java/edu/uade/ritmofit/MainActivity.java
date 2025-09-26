package edu.uade.ritmofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String emailLink = intent.getDataString();

        if (emailLink != null && auth.isSignInWithEmailLink(emailLink)) {
            try {

                MasterKey masterKey = new MasterKey.Builder(MainActivity.this)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build();

                SharedPreferences securePrefs = EncryptedSharedPreferences.create(
                        MainActivity.this,
                        "auth_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );

                String email = securePrefs.getString("emailForSignIn", null);
                String pword = securePrefs.getString("passwordToLink", null);

                // Retrieve the saved email from SharedPreferences
                // SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                // String email = prefs.getString("emailForSignIn", null);

                if (email != null && pword != null) {
                    auth.signInWithEmailAndPassword(email, pword)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    Log.d("Auth", "Successfully signed in!");
                                    FirebaseUser user = task.getResult().getUser();
                                    // TODO: move to main app screen


                                } else {
                                    Log.e("Auth", "Error signing in", task.getException());
                                }
                            });
                } else {
                    Log.e("Auth", "No email or password saved, cannot complete sign-in");
                }
            } catch (Exception e) {
                Log.e("Auth", "Failed to access secure storage");
            }
        }

    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }
}