package edu.uade.ritmofit;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null) {
            // showLogin();
        }
    }

    protected  void showLogin(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }

    protected void showSignUp(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SignupFragment())
                .addToBackStack(null) // allows going back to login with back button
                .commit();
    }

    protected void confirmLogin(View view) {
        EditText usernameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    AuthResult result = task.getResult();
                    FirebaseUser user = result.getUser();

                    // llevar al home
                });
    }

    protected void confirmSignUp(View view) {
        EditText usernameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

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

                        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                        prefs.edit().putString("emailForSignIn", username).apply();
                        prefs.edit().putString("passwordToLink", password).apply();
                    }
                });
    }
}
