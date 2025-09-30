package edu.uade.ritmofit.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.auth.repository.AuthRepository;
import edu.uade.ritmofit.historial.Model.ReservaDTO;
import edu.uade.ritmofit.home.repository.HomeViewModel;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private ListView turnosListView;
    
    HomeViewModel reservasViewModel;
    @Inject
    AuthRepository authRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        turnosListView = view.findViewById(R.id.listViewTurnos);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new ArrayList<>()
        );

        turnosListView.setAdapter(adapter);

        reservasViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        reservasViewModel.getReservas().observe(getViewLifecycleOwner(), reservas -> {
            List<String> texto = new ArrayList<>();
            for (String reserva : reservas) {
                texto.add(reserva);
            }
            adapter.clear();
            adapter.addAll(texto);
            adapter.notifyDataSetChanged();
        });

        turnosListView.setOnItemClickListener((adapterView, view1, i, l) -> {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    "Hacia la pagina del QR (coming soon)",
                    Snackbar.LENGTH_SHORT).show();
        } );

        reservasViewModel.cargarReservas();

        // Configurar botones de navegación
        Button btnProfile = view.findViewById(R.id.btn_profile);
        Button btnSedes = view.findViewById(R.id.btn_sedes);
        Button btnClasses = view.findViewById(R.id.btn_classes);
        Button btnHistorial = view.findViewById(R.id.btn_historial);


        NavController navController = Navigation.findNavController(view);
        btnHistorial.setOnClickListener(v -> navController.navigate(R.id.action_home_to_historial));



        btnProfile.setOnClickListener(v -> navController.navigate(R.id.action_home_to_profile));
        btnSedes.setOnClickListener(v -> {

            navController.navigate(R.id.action_home_to_sedes_graph);
        });
        btnClasses.setOnClickListener(v -> navController.navigate(R.id.action_home_to_classes));
    }

    @Override
    public void onResume(){
        super.onResume();
        reservasViewModel.cargarReservas();
    }
}