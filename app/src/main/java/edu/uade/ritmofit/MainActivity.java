package edu.uade.ritmofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.Modules.InterfaceService;
import edu.uade.ritmofit.repository.SedeRepository;
import edu.uade.ritmofit.repository.SedeRetrofitRepository;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        Button buttonSedes = findViewById(R.id.buttonSedes);

        buttonSedes.setOnClickListener(v -> {
            textView.setText("Botón Sedes presionado");
            Intent intent = new Intent(MainActivity.this, SedesActivity.class);
            startActivity(intent);
        });
    }
}