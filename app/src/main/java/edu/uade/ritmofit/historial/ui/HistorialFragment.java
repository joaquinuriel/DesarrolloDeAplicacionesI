package edu.uade.ritmofit.historial.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.historial.Model.Reserva;
import edu.uade.ritmofit.historial.Repository.InterfaceRepositoryHistorial;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@AndroidEntryPoint
public class HistorialFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservaAdapter adapter;
    private List<Reserva> reservaList = new ArrayList<>();

    @Inject
    InterfaceRepositoryHistorial historialRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historial_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        NavController navController = NavHostFragment.findNavController(this); // Obtener NavController
        adapter = new ReservaAdapter(reservaList, navController); // Pasar NavController al adaptador
        recyclerView.setAdapter(adapter);

        historialRepository.getHistorial(new InterfaceRepositoryHistorial.ReservasListCallback() {
            @Override
            public void onSuccess(List<Reserva> reservasList) {
                reservaList.clear();
                reservaList.addAll(reservasList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(requireContext(), "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}