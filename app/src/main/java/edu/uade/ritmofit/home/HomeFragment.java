package edu.uade.ritmofit.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.home.model.Turno;
import edu.uade.ritmofit.home.service.TurnoService;

public class HomeFragment extends Fragment {
    private ListView turnosListView;
    private TurnoService service;
    private List<Turno> listaTurnos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        service = new TurnoService();
        listaTurnos = service.getAllTurn();

        ArrayList<String> displayTurnos = new ArrayList<>();
        for (Turno turn : listaTurnos) {
            displayTurnos.add(turn.getClase() + "-" + turn.getSede() + "-" + turn.getFecha());
        }

        turnosListView = view.findViewById(R.id.listViewTurnos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                displayTurnos
        );

        turnosListView.setAdapter(adapter);

        return view;
    }
}