package edu.uade.ritmofit.historial.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.historial.Model.CalificacionDTO;
import edu.uade.ritmofit.historial.Model.ReservaDetail;
import edu.uade.ritmofit.historial.Repository.InterfaceRepositoryHistorial;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@AndroidEntryPoint
public class HistorialDetailFragment extends Fragment {

    private static final String ARG_RESERVA_ID = "reservaId";

    private TextView textViewIdReserva;
    private TextView textViewEstado;
    private TextView textViewTimestampCreacion;
    private TextView textViewTimestampCheckin;
    private TextView textViewConfirmedCheckin;
    private TextView textViewFechaClase;
    private TextView textViewFechaClaseDuracion;
    private TextView textViewDisciplina;
    private TextView textViewSedeNombre;
    private TextView textViewProfesorNombre;
    private RecyclerView recyclerViewCalificaciones;

    @Inject
    InterfaceRepositoryHistorial historialRepository;

    public static HistorialDetailFragment newInstance(String reservaId) {
        HistorialDetailFragment fragment = new HistorialDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RESERVA_ID, reservaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historial_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        textViewIdReserva = view.findViewById(R.id.textViewIdReserva);
        textViewEstado = view.findViewById(R.id.textViewEstado);
        textViewTimestampCreacion = view.findViewById(R.id.textViewTimestampCreacion);
        textViewTimestampCheckin = view.findViewById(R.id.textViewTimestampCheckin);
        textViewConfirmedCheckin = view.findViewById(R.id.textViewConfirmedCheckin);
        textViewFechaClase = view.findViewById(R.id.textViewFechaClase);
        textViewFechaClaseDuracion = view.findViewById(R.id.textViewFechaClaseDuracion);
        textViewDisciplina = view.findViewById(R.id.textViewDisciplina);
        textViewSedeNombre = view.findViewById(R.id.textViewSedeNombre);
        textViewProfesorNombre = view.findViewById(R.id.textViewProfesorNombre);
        recyclerViewCalificaciones = view.findViewById(R.id.recyclerViewCalificaciones);

        // Configurar RecyclerView para calificaciones
        recyclerViewCalificaciones.setLayoutManager(new LinearLayoutManager(requireContext()));
        CalificacionAdapter calificacionAdapter = new CalificacionAdapter(new ArrayList<>());
        recyclerViewCalificaciones.setAdapter(calificacionAdapter);

        // Obtener ID de la reserva desde los argumentos
        String reservaId = getArguments() != null ? getArguments().getString(ARG_RESERVA_ID) : null;
        if (reservaId == null) {
            Toast.makeText(requireContext(), "ID de reserva no proporcionado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener detalles de la reserva
        historialRepository.getReserva(reservaId, new InterfaceRepositoryHistorial.ReservaDetailCallback() {
            @Override
            public void onSuccess(ReservaDetail reservaDetail) {
                // Actualizar vistas con los datos
                textViewIdReserva.setText("ID: " + reservaDetail.getIdReserva());
                textViewEstado.setText("Estado: " + reservaDetail.getEstado());
                textViewTimestampCreacion.setText("Creado: " + reservaDetail.getTimestampCreacion());
                textViewTimestampCheckin.setText("Check-in: " + reservaDetail.getTimestampCheckin());
                textViewConfirmedCheckin.setText("Check-in Confirmado: " + reservaDetail.isConfirmedCheckin());
                textViewFechaClase.setText("Fecha Clase: " + reservaDetail.getFechaClase());
                textViewFechaClaseDuracion.setText("Duración: " + reservaDetail.getFechaClaseDuracion());
                textViewDisciplina.setText("Disciplina: " + reservaDetail.getDisciplina());
                textViewSedeNombre.setText("Sede: " + reservaDetail.getSedeNombre());
                textViewProfesorNombre.setText("Profesor: " + reservaDetail.getProfesorNombre());

                // Actualizar RecyclerView con calificaciones
                calificacionAdapter.updateCalificaciones(reservaDetail.getCalificaciones());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(requireContext(), "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Adaptador para las calificaciones
    private static class CalificacionAdapter extends RecyclerView.Adapter<CalificacionAdapter.CalificacionViewHolder> {

        private List<CalificacionDTO> calificaciones;

        public CalificacionAdapter(List<CalificacionDTO> calificaciones) {
            this.calificaciones = calificaciones != null ? calificaciones : new ArrayList<>();
        }

        public void updateCalificaciones(List<CalificacionDTO> newCalificaciones) {
            this.calificaciones = newCalificaciones != null ? newCalificaciones : new ArrayList<>();
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public CalificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new CalificacionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CalificacionViewHolder holder, int position) {
            CalificacionDTO calificacion = calificaciones.get(position);
            holder.textView.setText("Opinaron: " + (calificacion.getComentario() != null ? calificacion.getComentario() : "Sin valor"));
           // holder.textView.setText("Estrellas: " + calificacion.getEstrellas());
        }

        @Override
        public int getItemCount() {
            return calificaciones.size();
        }

        static class CalificacionViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            CalificacionViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }
    }
}