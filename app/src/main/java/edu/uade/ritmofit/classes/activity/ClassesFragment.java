package edu.uade.ritmofit.classes.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.classes.adapter.ClassesAdapter;
import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.classes.viewmodel.ClassesViewModel;

@AndroidEntryPoint
public class ClassesFragment extends Fragment {

    private AutoCompleteTextView sedeDropdown, disciplinaDropdown;
    private MaterialButton fechaButton, btnClearFilters;
    private RecyclerView clasesRecycler;
    private TextView mensajeTextView;


    private ClassesAdapter adapter;
    private ClassesViewModel viewModel;

    private String sedeSeleccionada = "Todos";
    private String disciplinaSeleccionada = "Todos";
    private String fechaSeleccionada = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_classes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sedeDropdown = view.findViewById(R.id.spSite);
        disciplinaDropdown = view.findViewById(R.id.spDiscipline);
        fechaButton = view.findViewById(R.id.btnDate);
        clasesRecycler = view.findViewById(R.id.rvClasses);
        mensajeTextView = view.findViewById(R.id.tvMessage);
        btnClearFilters = view.findViewById(R.id.btnClearFilters);

        clasesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ClassesAdapter(new ArrayList<>(), clase -> {
            Bundle args = new Bundle();
            args.putSerializable("arg_clase", clase);
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_classesFragment_to_classDetailFragment, args);
        });



        clasesRecycler.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ClassesViewModel.class);

        viewModel.getClases().observe(getViewLifecycleOwner(), clases -> {
            if (clases == null || clases.isEmpty()) {
                mostrarMensaje("No hay clases disponibles.");
            } else {
                mensajeTextView.setVisibility(View.GONE);
                adapter.updateData(clases);

                if (sedeDropdown.getAdapter() == null || disciplinaDropdown.getAdapter() == null) {
                    setupDropdowns(clases);
                }
            }
        });




        viewModel.getMensaje().observe(getViewLifecycleOwner(), this::mostrarMensaje);

        fechaButton.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dp = new DatePickerDialog(requireContext(),
                    (picker, year, month, dayOfMonth) -> {
                        fechaSeleccionada = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                        fechaButton.setText(fechaSeleccionada);
                        aplicarFiltros();
                    },
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dp.show();
        });

        btnClearFilters.setOnClickListener(v -> {
            sedeSeleccionada = "Todos";
            disciplinaSeleccionada = "Todos";
            fechaSeleccionada = null;

            sedeDropdown.setText("Todos", false);
            disciplinaDropdown.setText("Todos", false);
            fechaButton.setText("Fecha");

            aplicarFiltros();
        });


        viewModel.cargarClases();
    }


    private void setupDropdowns(List<Clase> clases) {
        List<String> sedes = new ArrayList<>();
        List<String> disciplinas = new ArrayList<>();

        sedes.add("Todos");
        disciplinas.add("Todos");

        for (Clase c : clases) {
            if (c.getSedeNombre() != null && !sedes.contains(c.getSedeNombre())) {
                sedes.add(c.getSedeNombre());
            }
            if (c.getDisciplina() != null && !disciplinas.contains(c.getDisciplina())) {
                disciplinas.add(c.getDisciplina());
            }
        }

        ArrayAdapter<String> sedeAdapter =
                new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, sedes);
        ArrayAdapter<String> discAdapter =
                new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, disciplinas);

        sedeDropdown.setAdapter(sedeAdapter);
        disciplinaDropdown.setAdapter(discAdapter);

        sedeDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSede = (String) parent.getItemAtPosition(position);
            viewModel.filtrar(selectedSede, null, null);
        });

        disciplinaDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDisciplina = (String) parent.getItemAtPosition(position);
            viewModel.filtrar(null, selectedDisciplina, null);
        });

    }


    private void aplicarFiltros() {
        viewModel.filtrar(sedeSeleccionada, disciplinaSeleccionada, fechaSeleccionada);
    }

    private void mostrarMensaje(String mensaje) {
        clasesRecycler.setAdapter(null);
        mensajeTextView.setVisibility(View.VISIBLE);
        mensajeTextView.setText(mensaje);
    }
}
