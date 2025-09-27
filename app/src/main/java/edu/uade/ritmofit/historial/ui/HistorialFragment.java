package edu.uade.ritmofit.historial.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.historial.Model.Reserva;
import edu.uade.ritmofit.historial.Repository.InterfaceRepositoryHistorial;
import javax.inject.Inject;

@AndroidEntryPoint
public class HistorialFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservaAdapter adapter;
    private List<Reserva> reservaList = new ArrayList<>();
    private List<Reserva> reservaListFull = new ArrayList<>(); // Lista completa para restaurar
    private EditText editTextFechaFiltro;

    private Button buttonBorrarFiltro;

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

        // Inicializar UI
        recyclerView = view.findViewById(R.id.recyclerViewHistorial);
        editTextFechaFiltro = view.findViewById(R.id.editTextFechaFiltro);

        buttonBorrarFiltro = view.findViewById(R.id.buttonBorrarFiltro);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        NavController navController = NavHostFragment.findNavController(this);
        adapter = new ReservaAdapter(reservaList, navController);
        recyclerView.setAdapter(adapter);

        // Configurar DatePickerDialog
        editTextFechaFiltro.setOnClickListener(v -> showDatePickerDialog());

        // Configurar botón de borrar filtro
        buttonBorrarFiltro.setOnClickListener(v -> {
            editTextFechaFiltro.setText("");
            loadReservas();
        });

        // Cargar datos iniciales
        loadReservas();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Formatear la fecha seleccionada como yyyy-MM-dd
                    String selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    editTextFechaFiltro.setText(selectedDate);
                    filterReservas(selectedDate); // Aplicar filtro al confirmar la fecha
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void loadReservas() {
        historialRepository.getHistorial(new InterfaceRepositoryHistorial.ReservasListCallback() {
            @Override
            public void onSuccess(List<Reserva> reservasList) {
                reservaListFull.clear();
                reservaListFull.addAll(reservasList); // Guardar lista completa
                reservaList.clear();
                reservaList.addAll(reservasList); // Mostrar todas inicialmente
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(requireContext(), "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filterReservas(String fechaFiltro) {
        reservaList.clear();
        if (fechaFiltro.isEmpty()) {
            // Si no hay filtro, mostrar todas las reservas
            reservaList.addAll(reservaListFull);
        } else {
            // Filtrar por fecha
            for (Reserva reserva : reservaListFull) {
                if (reserva.getFecha().equals(fechaFiltro)) {
                    reservaList.add(reserva);
                }
            }
        }
        adapter.notifyDataSetChanged();

        // Mostrar mensaje si no hay resultados
        if (reservaList.isEmpty()) {
            Toast.makeText(requireContext(), "No se encontraron reservas para la fecha seleccionada", Toast.LENGTH_SHORT).show();
        }
    }
}