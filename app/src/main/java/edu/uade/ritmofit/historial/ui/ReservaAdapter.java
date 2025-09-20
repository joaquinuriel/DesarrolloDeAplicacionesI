package edu.uade.ritmofit.historial.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.historial.Model.Reserva;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private List<Reserva> reservaList;

    public ReservaAdapter(List<Reserva> reservaList) {
        this.reservaList = (reservaList != null) ? reservaList : new ArrayList<>();
    }

    @Override
    public ReservaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReservaViewHolder holder, int position) {
        Reserva reserva = reservaList.get(position);
        holder.textViewIdReserva.setText("ID: " + reserva.getIdReserva());
        holder.textViewDisciplina.setText("Disciplina: " + reserva.getDisciplina());
        holder.textViewNombreSede.setText("Sede: " + reserva.getNombre());
        holder.textViewFecha.setText("Fecha: " + reserva.getFecha());

        // Botón sin funcionalidad por ahora
        holder.buttonAccion.setOnClickListener(v -> {
            // TODO: Implementar funcionalidad del botón aquí
        });
    }

    @Override
    public int getItemCount() {
        return reservaList.size();
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIdReserva, textViewDisciplina, textViewNombreSede, textViewFecha;
        Button buttonAccion;

        public ReservaViewHolder(View itemView) {
            super(itemView);
            textViewIdReserva = itemView.findViewById(R.id.textViewIdReserva);
            textViewDisciplina = itemView.findViewById(R.id.textViewDisciplina);
            textViewNombreSede = itemView.findViewById(R.id.textViewNombreSede);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            buttonAccion = itemView.findViewById(R.id.buttonAccion);
        }
    }
}