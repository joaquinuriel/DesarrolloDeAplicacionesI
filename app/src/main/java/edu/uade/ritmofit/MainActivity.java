package edu.uade.ritmofit;

import androidx.appcompat.app.AppCompatActivity;

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
            // Retrieve the saved email from SharedPreferences
            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            String email = prefs.getString("emailForSignIn", null);

            if (email != null) {
                auth.signInWithEmailLink(email, emailLink)
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
                // Email not saved, ask user to input it
                Log.e("Auth", "No email saved, cannot complete sign-in");
            }
        }

    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}