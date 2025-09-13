package edu.uade.ritmofit.classes.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.classes.model.Class;
import edu.uade.ritmofit.classes.service.ClassApiService;
import edu.uade.ritmofit.classes.service.ProfesorApiService;
import edu.uade.ritmofit.classes.service.SedeApiService;
import edu.uade.ritmofit.classes.adapter.ClassesAdapter;
import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.classes.model.Sede;
import android.app.DatePickerDialog;
import edu.uade.ritmofit.data.api.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassesFragment extends Fragment {

    private AutoCompleteTextView spSite, spDiscipline;
    private MaterialButton btnDate;
    private RecyclerView rvClasses;
    private TextView tvMessage;

    private List<Class> allClasses = new ArrayList<>();
    private List<Class> filtered = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);

        spSite = view.findViewById(R.id.spSite);
        spDiscipline = view.findViewById(R.id.spDiscipline);
        btnDate = view.findViewById(R.id.btnDate);
        rvClasses = view.findViewById(R.id.rvClasses);
        tvMessage = view.findViewById(R.id.tvMessage);

        rvClasses.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadClassesFromApi();

        btnDate.setOnClickListener(v -> {
            String current = btnDate.getText().toString();
            if (!current.equals("Fecha")) {
                btnDate.setText("Fecha");
                applyFilters();
            } else {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dp = new DatePickerDialog(requireContext(),
                        (view1, year, month, dayOfMonth) -> {
                            String date = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                            btnDate.setText(date);
                            applyFilters();
                        },
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });

        return view;
    }

    private void loadClassesFromApi() {
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText("Cargando...");

        ApiClient.createService(ClassApiService.class).getClasses().enqueue(new Callback<List<Class>>() {
            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allClasses = response.body();
                    filtered = new ArrayList<>(allClasses);

                    for (Class c : allClasses) {
                        loadProfesorForClass(c);
                        loadSedeForClass(c);
                    }
                    refreshList();
                } else {
                    showMessage("No hay clases disponibles.");
                }
            }

            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {
                Log.e("API_ERROR", "Error en Retrofit", t);
                showMessage("Ha surgido un error cargando las clases");
            }
        });
    }

    private void loadProfesorForClass(Class clase) {
        if (clase.getIdProfesor() == null) return;

        ApiClient.createService(ProfesorApiService.class)
                .getProfesor(clase.getIdProfesor())
                .enqueue(new Callback<Profesor>() {
                    @Override
                    public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Profesor profesor = response.body();
                            clase.setProfesor(profesor.getNombre());
                            refreshList();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profesor> call, Throwable t) {
                        Log.e("API_ERROR", "Error cargando profesor", t);
                    }
                });
    }

    private void loadSedeForClass(Class clase) {
        if (clase.getIdSede() == null) return;

        ApiClient.createService(SedeApiService.class)
                .getSede(clase.getIdSede())
                .enqueue(new Callback<Sede>() {
                    @Override
                    public void onResponse(Call<Sede> call, Response<Sede> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Sede sede = response.body();
                            clase.setSede(sede.getNombre() + " - " + sede.getBarrio());
                            refreshList();

                            setupDynamicFilters(allClasses);
                        }
                    }

                    @Override
                    public void onFailure(Call<Sede> call, Throwable t) {
                        Log.e("API_ERROR", "Error cargando sede", t);
                    }
                });
    }

    private void setupDynamicFilters(List<Class> clases) {
        Set<String> sedes = new HashSet<>();
        Set<String> disciplinas = new HashSet<>();

        for (Class c : clases) {
            if (c.getIdSede() != null) sedes.add(c.getIdSede());
            if (c.getDisciplina() != null) disciplinas.add(c.getDisciplina());
        }

        List<String> sedeList = new ArrayList<>();
        sedeList.add("Todas");
        sedeList.addAll(sedes);

        List<String> disciplinaList = new ArrayList<>();
        disciplinaList.add("Todas");
        disciplinaList.addAll(disciplinas);

        spSite.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, sedeList));
        spDiscipline.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, disciplinaList));

        spSite.setOnItemClickListener((parent, view, pos, id) -> applyFilters());
        spDiscipline.setOnItemClickListener((parent, view, pos, id) -> applyFilters());
    }

    private void applyFilters() {
        String site = spSite.getText().toString();
        String discipline = spDiscipline.getText().toString();
        String date = btnDate.getText().toString();

        filtered.clear();
        for (Class it : allClasses) {
            boolean matches = true;

            if (!site.equals("Todas") && !site.isEmpty()) {
                matches = matches && it.getIdSede() != null && it.getIdSede().contains(site);
            }

            if (!discipline.equals("Todas") && !discipline.isEmpty()) {
                matches = matches && it.getDisciplina() != null && it.getDisciplina().equals(discipline);
            }

            if (!date.equals("Fecha")) {
                matches = matches && it.getFecha() != null && it.getFecha().equals(date);
            }

            if (matches) filtered.add(it);
        }

        refreshList();
    }

    private void refreshList() {
        if (filtered.isEmpty()) {
            showMessage("No hay clases para los filtros seleccionados.");
        } else {
            tvMessage.setVisibility(View.GONE);
            rvClasses.setAdapter(new ClassesAdapter(filtered, item -> {
                Bundle bundle = new Bundle();
                bundle.putString("classId", item.getIdClase());
                bundle.putString("disciplina", item.getDisciplina());
                bundle.putString("fecha", item.getFecha());
                bundle.putString("hora", item.getHorarioInicio());
                bundle.putDouble("duracion", item.getDuracion());
                bundle.putString("profesor", item.getIdProfesor());
                bundle.putString("sede", item.getIdSede());
                bundle.putString("estado", item.getEstado());
                Navigation.findNavController(requireView()).navigate(R.id.action_classes_to_detail, bundle);
            }));
        }
    }

    private void showMessage(String msg) {
        rvClasses.setAdapter(null);
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText(msg);
    }
}