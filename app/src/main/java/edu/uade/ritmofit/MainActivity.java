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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SedeViewModel viewModel;
    private RecyclerView recyclerViewSedes;
    private SedeAdapter sedeAdapter;
    private SearchView searchViewSedes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedes_main);

        recyclerViewSedes = findViewById(R.id.recyclerViewSedes);
        recyclerViewSedes.setLayoutManager(new LinearLayoutManager(this));

        // Agregar DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewSedes.addItemDecoration(dividerItemDecoration);

        sedeAdapter = new SedeAdapter(null);
        recyclerViewSedes.setAdapter(sedeAdapter);

        // Configurar el SearchView
        searchViewSedes = findViewById(R.id.searchViewSedes);
        searchViewSedes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Opcional: Puedes manejar la búsqueda al presionar "Enter"
                sedeAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtra en tiempo real mientras se escribe
                sedeAdapter.filter(newText);
                return true;
            }
        });

        viewModel = new ViewModelProvider(this).get(SedeViewModel.class);

        viewModel.getSedes().observe(this, new Observer<List<SedeDto>>() {
            @Override
            public void onChanged(List<SedeDto> sedes) {
                if (sedes != null) {
                    sedeAdapter.updateData(sedes);
                } else {
                    // Opcional: Mostrar mensaje de error
                }
            }
        });

        viewModel.fetchSedes();
    }
}