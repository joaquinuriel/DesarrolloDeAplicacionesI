package edu.uade.ritmofit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
    }

    public void onBack(View view) {
        finish();
    }

    public void onConfirm(View view) {
        // TODO: Navegar al Home
    }
}
