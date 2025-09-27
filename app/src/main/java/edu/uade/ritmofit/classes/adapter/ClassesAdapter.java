package edu.uade.ritmofit.classes.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.classes.model.Clase;
import java.util.List;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassViewHolder> {

    public interface OnItemClick {
        void onClick(Clase item);
    }

    private final List<Clase> data;
    private final OnItemClick listener;

    public ClassesAdapter(List<Clase> data, OnItemClick listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMeta, tvInstructor;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMeta = itemView.findViewById(R.id.tvMeta);
            tvInstructor = itemView.findViewById(R.id.tvInstructor);
        }

        public void bind(Clase it, OnItemClick listener) {
            tvTitle.setText(it.getDisciplina());

            tvMeta.setText(
                    it.getFecha() + " • " + it.getHorarioInicio().substring(0,5) + " hs" +
                            " • " + it.getDuracion() + " min • Cupos: " + it.getCupo() +
                            "\nSede: " + (it.getSedeNombre() != null ? it.getSedeNombre() : it.getIdSede())
            );

            tvInstructor.setText("Profesor: " +
                    (it.getProfesorNombre() != null ? it.getProfesorNombre() : it.getIdProfesor()));


            itemView.setOnClickListener(v -> listener.onClick(it));

        }
    }

    public void updateData(List<Clase> nuevasClases) {
        data.clear();
        data.addAll(nuevasClases);
        notifyDataSetChanged();
    }

}