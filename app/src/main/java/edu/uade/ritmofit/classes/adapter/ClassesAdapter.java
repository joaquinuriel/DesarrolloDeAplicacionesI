package edu.uade.ritmofit.classes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.classes.model.Class;
import java.util.List;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassViewHolder> {

    public interface OnItemClick {
        void onClick(Class item);
    }

    private final List<Class> data;
    private final OnItemClick listener;

    public ClassesAdapter(List<Class> data, OnItemClick listener) {
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

        public void bind(Class it, OnItemClick listener) {
            tvTitle.setText(it.getDisciplina());

            tvMeta.setText(
                    it.getFecha() + " • " + it.getHorarioInicio().substring(0,5) + " hs"+
                            " • " + it.getDuracion() + " min • Cupos: " + it.getCupo() +
                            "\nSede: " + it.getIdSede()
            );



            tvInstructor.setText("Profesor: " + it.getIdProfesor());

            itemView.setOnClickListener(v -> listener.onClick(it));
        }
    }
}