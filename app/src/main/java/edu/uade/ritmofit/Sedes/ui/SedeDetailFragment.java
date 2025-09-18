package edu.uade.ritmofit.Sedes.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.Sedes.Model.SedeDto;
import edu.uade.ritmofit.Sedes.viewModel.SedeViewModel;

public class SedeDetailFragment extends Fragment {
    private static final String TAG = "SedeDetailFragment";
    private SedeViewModel viewModel;
    private TextView detailNombre;
    private TextView detailUbicacion;
    private TextView detailBarrio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sede_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        detailNombre = view.findViewById(R.id.detailNombre);
        detailUbicacion = view.findViewById(R.id.detailUbicacion);
        detailBarrio = view.findViewById(R.id.detailBarrio);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(SedeViewModel.class);

        // Obtener sedeId de los argumentos
        String sedeId = getArguments() != null ? getArguments().getString("sedeId") : null;
        if (sedeId != null) {
            viewModel.fetchSedeDetail(sedeId); // Llamar a fetchSedeDetail con el ID
        }

        // Observar los detalles de la sede
        viewModel.getSedeDetail().observe(getViewLifecycleOwner(), sede -> {
            if (sede != null) {
                Log.d(TAG, "Detalle de sede recibido: " + sede.getId_sede());
                detailNombre.setText(sede.getNombre());
                detailUbicacion.setText(sede.getUbicacion());
                detailBarrio.setText(sede.getBarrio());
            }
        });

        // Observar errores
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Log.w(TAG, "Error recibido: " + error);
                // Podrías mostrar el error en un Toast
            }
        });
    }
}