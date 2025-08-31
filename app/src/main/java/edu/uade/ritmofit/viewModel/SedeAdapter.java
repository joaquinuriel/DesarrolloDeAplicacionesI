package edu.uade.ritmofit.viewModel;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.data.api.model.SedeResponse;

public class SedeAdapter extends RecyclerView.Adapter<SedeAdapter.SedeViewHolder> {

    private List<SedeResponse> sedes;

    public SedeAdapter(List<SedeResponse> sedes) {
        this.sedes = sedes;
    }

    @NonNull
    @Override
    public SedeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sede, parent, false);
        return new SedeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SedeViewHolder holder, int position) {
        SedeResponse sede = sedes.get(position);
        holder.sedeNombre.setText(sede.getNombre());
        holder.sedeUbicacion.setText(sede.getUbicacion());
    }

    @Override
    public int getItemCount() {
        return sedes != null ? sedes.size() : 0;
    }

    public void updateSedes(List<SedeResponse> newSedes) {
        this.sedes = newSedes;
        notifyDataSetChanged();
    }

    static class SedeViewHolder extends RecyclerView.ViewHolder {
        TextView sedeNombre;
        TextView sedeUbicacion;

        public SedeViewHolder(@NonNull View itemView) {
            super(itemView);
            sedeNombre = itemView.findViewById(R.id.sedeNombre);
            sedeUbicacion = itemView.findViewById(R.id.sedeUbicacion);
        }
    }
}