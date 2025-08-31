package edu.uade.ritmofit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.data.api.model.SedeResponse;
import edu.uade.ritmofit.viewModel.SedeViewModel;

@AndroidEntryPoint
public class SedeDetailActivity extends AppCompatActivity {
    private SedeViewModel viewModel;
    private TextView detailNombre;
    private TextView detailUbicacion;
    private TextView detailBarrio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sede_detail);

        // Inicializar vistas
        detailNombre = findViewById(R.id.detailNombre);
        detailUbicacion = findViewById(R.id.detailUbicacion);
        detailBarrio = findViewById(R.id.detailBarrio);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(SedeViewModel.class);

        // Obtener el ID de la sede desde el Intent
        String sedeId = getIntent().getStringExtra("sedeId");
        if (sedeId != null) {
            viewModel.fetchSedeDetail(sedeId); // Solicitar detalles
        }

        // Observar los detalles de la sede
        viewModel.getSedeDetail().observe(this, new Observer<SedeResponse>() {
            @Override
            public void onChanged(SedeResponse sede) {
                if (sede != null) {
                    detailNombre.setText(sede.getNombre());
                    detailUbicacion.setText(sede.getUbicacion());
                    detailBarrio.setText(sede.getBarrio());
                }
            }
        });

        // Observar errores
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    // Podrías mostrar el error en un Toast
                }
            }
        });

        // Opcional: Agregar animación de retorno
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Opcional: Agregar animación de salida
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}