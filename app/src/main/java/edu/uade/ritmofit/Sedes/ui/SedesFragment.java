package edu.uade.ritmofit.Sedes.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.hilt.android.AndroidEntryPoint;



import edu.uade.ritmofit.R;
@AndroidEntryPoint
public class SedesFragment extends Fragment {
    private static final String TAG = "SedesFragment";
    private SedeViewModel viewModel ;
    private RecyclerView recyclerViewSedes;
    private SedeAdapter sedeAdapter;
    private SearchView searchViewSedes;
    private TextView errorTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sedes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        recyclerViewSedes = view.findViewById(R.id.recyclerViewSedes);
        errorTextView = view.findViewById(R.id.tvError);
        searchViewSedes = view.findViewById(R.id.searchViewSedes);

        if (recyclerViewSedes == null) {
            Log.e(TAG, "recyclerViewSedes no encontrado en el layout");
            return;
        }
        recyclerViewSedes.setLayoutManager(new LinearLayoutManager(requireContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerViewSedes.addItemDecoration(dividerItemDecoration);

        sedeAdapter = new SedeAdapter(sede -> {
            Bundle bundle = new Bundle();
            bundle.putString("sedeId", sede.getId_sede());
            Navigation.findNavController(view).navigate(R.id.action_sedes_to_sedeDetail, bundle);
        });
        recyclerViewSedes.setAdapter(sedeAdapter);

        // Configurar SearchView
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

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(SedeViewModel.class);

        // Observar cambios en los datos
        viewModel.getSedes().observe(getViewLifecycleOwner(), sedes -> {
            if (sedes != null) {
                Log.d(TAG, "Datos recibidos: " + sedes.size() + " sedes");
                sedeAdapter.updateData(sedes);
                errorTextView.setVisibility(View.GONE);
            } else {
                Log.w(TAG, "Datos nulos recibidos del ViewModel");
            }
        });

        // Observar errores
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Log.w(TAG, "Error recibido: " + error);
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(error);
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        // Cargar datos iniciales
        viewModel.fetchSedes();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (searchViewSedes != null) {
            searchViewSedes.setOnQueryTextListener(null);
        }
    }
}