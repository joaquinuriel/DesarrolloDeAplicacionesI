package edu.uade.ritmofit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.data.api.model.SedeResponse;
import edu.uade.ritmofit.viewModel.SedeAdapter;
import edu.uade.ritmofit.viewModel.SedeViewModel;

@AndroidEntryPoint
public class SedesActivity extends AppCompatActivity {
    private SedeViewModel viewModel;
    private RecyclerView recyclerViewSedes;
    private SedeAdapter sedeAdapter;
    private List<SedeResponse> allSedes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedesmain);

        // Inicializar RecyclerView
        recyclerViewSedes = findViewById(R.id.recyclerViewSedes);
        recyclerViewSedes.setLayoutManager(new LinearLayoutManager(this));
        sedeAdapter = new SedeAdapter(new ArrayList<>());
        recyclerViewSedes.setAdapter(sedeAdapter);

        // Inicializar ViewModel con Hilt
        viewModel = new ViewModelProvider(this).get(SedeViewModel.class);

        // Observar los datos de las sedes
        viewModel.getSedes().observe(this, new Observer<List<SedeResponse>>() {
            @Override
            public void onChanged(List<SedeResponse> sedes) {
                if (sedes != null) {
                    allSedes.clear();
                    allSedes.addAll(sedes);
                    sedeAdapter.updateSedes(sedes); // Actualizar el adaptador con los datos
                }
            }
        });

        // Observar errores
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    // Podrías mostrar el error en un Toast o un TextView adicional
                }
            }
        });

        // Configurar SearchView
        androidx.appcompat.widget.SearchView searchViewSedes = findViewById(R.id.searchViewSedes);
        searchViewSedes.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSedes(newText);
                return true;
            }
        });
    }

    private void filterSedes(String query) {
        List<SedeResponse> filteredList = new ArrayList<>();
        for (SedeResponse sede : allSedes) {
            if (sede.getNombre().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(sede);
            }
        }
        sedeAdapter.updateSedes(filteredList);
    }
}