package edu.uade.ritmofit.Sedes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.Sedes.viewModel.SedeViewModel;

import edu.uade.ritmofit.Sedes.Model.SedeResponse;
import edu.uade.ritmofit.Sedes.SedeAdapter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class SedesActivity extends AppCompatActivity {
    private static final String TAG = "SedeActivity";
    private SedeViewModel viewModel;
    private RecyclerView recyclerViewSedes;
    private SedeAdapter sedeAdapter;
    private SearchView searchViewSedes;
    private TextView errorTextView; // Para mostrar errores

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedes_main);
        Log.d(TAG, "onCreate: Actividad iniciada");

        // Inicializar vistas
        recyclerViewSedes = findViewById(R.id.recyclerViewSedes);
        errorTextView = findViewById(R.id.tvError);
        if (recyclerViewSedes == null) {
            Log.e(TAG, "recyclerViewSedes no encontrado en el layout");
            return;
        }
        recyclerViewSedes.setLayoutManager(new LinearLayoutManager(this));

        // Agregar decoración de división
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewSedes.addItemDecoration(dividerItemDecoration);

        // Inicializar adaptador con un callback para detalles
        sedeAdapter = new SedeAdapter(sede -> {
            viewModel.fetchSedeDetail(sede.getId_sede()); // Usamos getId_sede() según SedeResponse
            observeSedeDetail();
        });
        recyclerViewSedes.setAdapter(sedeAdapter);

        // Configurar SearchView con debounce básico
        searchViewSedes = findViewById(R.id.searchViewSedes);
        if (searchViewSedes == null) {
            Log.e(TAG, "searchViewSedes no encontrado en el layout");
            return; // Salir si el SearchView no existe
        }
        searchViewSedes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sedeAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 1 || newText.isEmpty()) {
                    sedeAdapter.filter(newText);
                }
                return true;
            }
        });

        // Inicializar ViewModel con Hilt
        viewModel = new ViewModelProvider(this).get(SedeViewModel.class);

        // Observar cambios en los datos de las sedes
        viewModel.getSedes().observe(this, sedes -> {
            if (sedes != null) {
                Log.d(TAG, "Datos recibidos: " + sedes.size() + " sedes");
                sedeAdapter.updateData(sedes);
                errorTextView.setVisibility(View.GONE); // Ocultar mensaje de error si hay datos
            } else {
                Log.w(TAG, "Datos nulos recibidos del ViewModel");
            }
        });

        // Observar errores
        viewModel.getError().observe(this, error -> {
            if (error != null) {
                Log.w(TAG, "Error recibido: " + error);
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(error);
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        // Cargar datos iniciales
        viewModel.fetchSedes(); // Ahora público
        Log.d(TAG, "onCreate: Finalizado");
    }

    // Método para observar los detalles de una sede
    private void observeSedeDetail() {
        viewModel.getSedeDetail().observe(this, sedeDetail -> {
            if (sedeDetail != null) {
                Log.d(TAG, "Detalle de sede recibido: " + sedeDetail.getId_sede());
                // Mostrar detalles en un Toast o navegar a otro Fragment/Activity
                String detailMessage = "Nombre: " + sedeDetail.getNombre() + "\nUbicación: " + sedeDetail.getUbicacion() + "\nBarrio: " + sedeDetail.getBarrio();
                Toast.makeText(this, detailMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (searchViewSedes != null) {
            searchViewSedes.setOnQueryTextListener(null);
        }
    }
}