package edu.uade.ritmofit.historial.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.historial.Model.Reserva;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private List<Reserva> reservaList;
    private NavController navController;

    public ReservaAdapter(List<Reserva> reservaList, NavController navController) {
        this.reservaList = (reservaList != null) ? reservaList : new ArrayList<>();
        this.navController = navController;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = reservaList.get(position);
        holder.textViewIdReserva.setText("ID: " + (reserva.getIdReserva() != null ? reserva.getIdReserva() : "N/A"));
        holder.textViewDisciplina.setText("Disciplina: " + (reserva.getDisciplina() != null ? reserva.getDisciplina() : "N/A"));
        holder.textViewNombreSede.setText("Sede: " + (reserva.getNombre() != null ? reserva.getNombre() : "N/A"));
        holder.textViewFecha.setText("Fecha: " + (reserva.getFecha() != null ? reserva.getFecha() : "N/A"));

        holder.buttonAccion.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("reservaId", reserva.getIdReserva());
            navController.navigate(R.id.action_historial_to_detail, args);
        });
    }

    @Override
    public int getItemCount() {
        return reservaList.size();
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIdReserva, textViewDisciplina, textViewNombreSede, textViewFecha;
        Button buttonAccion;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdReserva = itemView.findViewById(R.id.textViewIdReserva);
            textViewDisciplina = itemView.findViewById(R.id.textViewDisciplina);
            textViewNombreSede = itemView.findViewById(R.id.textViewNombreSede);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            buttonAccion = itemView.findViewById(R.id.buttonAccion);
        }
    }
}