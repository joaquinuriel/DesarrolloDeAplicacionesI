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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.auth.repository.AuthRepository;
import edu.uade.ritmofit.home.model.Turno;
import edu.uade.ritmofit.home.service.TurnoService;
@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private ListView turnosListView;
    private TurnoService service;
    private List<Turno> listaTurnos;


    @Inject
    AuthRepository authRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar servicio y lista de turnos
        service = new TurnoService();
        listaTurnos = service.getAllTurn();

        // Preparar datos para mostrar en el ListView
        ArrayList<String> displayTurnos = new ArrayList<>();
        for (Turno turn : listaTurnos) {
            displayTurnos.add(turn.getClase() + "-" + turn.getSede() + "-" + turn.getFecha());
        }

        // Configurar ListView
        turnosListView = view.findViewById(R.id.listViewTurnos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                displayTurnos
        );
        turnosListView.setAdapter(adapter);

        // Configurar botones de navegación
        Button btnProfile = view.findViewById(R.id.btn_profile);
        Button btnSedes = view.findViewById(R.id.btn_sedes);
        Button btnClasses = view.findViewById(R.id.btn_classes);
        Button btnLogout = view.findViewById(R.id.btn_logout);


        NavController navController = Navigation.findNavController(view);

        btnProfile.setOnClickListener(v -> navController.navigate(R.id.action_home_to_profile));
        btnSedes.setOnClickListener(v -> navController.navigate(R.id.action_home_to_sedes));
        btnClasses.setOnClickListener(v -> navController.navigate(R.id.action_home_to_classes));
        btnLogout.setOnClickListener(v -> {
            authRepository.clearAccessToken();
            navController.navigate(R.id.action_home_to_login);
        });
    }
}