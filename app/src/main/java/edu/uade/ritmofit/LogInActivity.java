package edu.uade.ritmofit;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    public void onWillSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onWillLogin(View view) {
        EditText emailInput = findViewById(R.id.editTextEmail);
        String userEmail = emailInput.getText().toString();

        Log.d(TAG, "Email: " + userEmail);

        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl("https://desarrollo-i.web.app/__/auth/action")
                .setHandleCodeInApp(true)
                .setAndroidPackageName("edu.uade.ritmofit", true, "12")
                .build();

        FirebaseAuth.getInstance()
                .sendSignInLinkToEmail(userEmail, actionCodeSettings)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent: " + userEmail);

                        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                        prefs.edit().putString("emailForSignIn", userEmail).apply();

                        // Intent intent = new Intent(this, PasswordActivity.class);
                        // startActivity(intent);
                    }
                });


    }
}
