package edu.uade.ritmofit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;
import edu.uade.ritmofit.Sedes.ViewModel.SedeViewModel;
import edu.uade.ritmofit.Sedes.Model.SedeDto;
import edu.uade.ritmofit.Sedes.SedeAdapter;
import android.util.Log;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SedeViewModel viewModel;
    private RecyclerView recyclerViewSedes;
    private SedeAdapter sedeAdapter;
    private SearchView searchViewSedes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedes_main);
        Log.d(TAG, "onCreate: Actividad iniciada");

        // Inicializar vistas
        recyclerViewSedes = findViewById(R.id.recyclerViewSedes);
        if (recyclerViewSedes == null) {
            Log.e(TAG, "recyclerViewSedes no encontrado en el layout");
            return; // Salir si el RecyclerView no existe
        }
        recyclerViewSedes.setLayoutManager(new LinearLayoutManager(this));

        // Agregar decoración de división
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewSedes.addItemDecoration(dividerItemDecoration);

        // Inicializar adaptador
        sedeAdapter = new SedeAdapter(null);
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
                // Filtrar solo si el texto cambia significativamente
                if (newText.length() > 1 || newText.isEmpty()) {
                    sedeAdapter.filter(newText);
                }
                return true;
            }
        });

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(SedeViewModel.class);

        // Observar cambios en los datos
        viewModel.getSedes().observe(this, new Observer<List<SedeDto>>() {
            @Override
            public void onChanged(List<SedeDto> sedes) {
                if (sedes != null) {
                    Log.d(TAG, "Datos recibidos: " + sedes.size() + " sedes");
                    sedeAdapter.updateData(sedes);
                } else {
                    Log.w(TAG, "Datos nulos recibidos del ViewModel");
                }
            }
        });

        // Cargar datos
        viewModel.fetchSedes();
        Log.d(TAG, "onCreate: Finalizado");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos si es necesario (por ejemplo, limpiar el SearchView)
        if (searchViewSedes != null) {
            searchViewSedes.setOnQueryTextListener(null);
        }
    }
}