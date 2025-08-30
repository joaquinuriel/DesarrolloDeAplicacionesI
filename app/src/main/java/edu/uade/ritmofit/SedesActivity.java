package edu.uade.ritmofit;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.data.api.model.SedeResponse;
import edu.uade.ritmofit.viewModel.SedeViewModel;

@AndroidEntryPoint
public class SedesActivity extends AppCompatActivity {
    private TextView textView;
    private SedeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedesmain);

        textView = findViewById(R.id.textView); // Asegúrate de que el ID coincida con tu layout

        // Inicializar ViewModel con Hilt
        viewModel = new ViewModelProvider(this).get(SedeViewModel.class);

        // Observar los datos de las sedes
        viewModel.getSedes().observe(this, new Observer<List<SedeResponse>>() {
            @Override
            public void onChanged(List<SedeResponse> sedes) {
                if (sedes != null) {
                    StringBuilder result = new StringBuilder();
                    for (SedeResponse sede : sedes) {
                        result.append("ID: ").append(sede.getId_sede())
                                .append(", Nombre: ").append(sede.getNombre())
                                .append(", Dirección: ").append(sede.getUbicacion())
                                .append("\n");
                    }
                    textView.setText(result.toString());
                }
            }
        });

        // Observar errores
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    textView.setText(error);
                }
            }
        });


    }
}