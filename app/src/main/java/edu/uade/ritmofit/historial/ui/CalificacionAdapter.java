package edu.uade.ritmofit.historial.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uade.ritmofit.historial.Model.CalificacionDTO;

public class CalificacionAdapter  extends RecyclerView.Adapter<CalificacionAdapter.CalificacionViewHolder> {

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
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            return new CalificacionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CalificacionViewHolder holder, int position) {
            CalificacionDTO calificacion = calificaciones.get(position);
            holder.textViewLine1.setText("Comentario: " + (calificacion.getComentario() != null ? calificacion.getComentario() : "Sin comentario"));
            holder.textViewLine2.setText("Estrellas: " + calificacion.getEstrellas());
        }

        @Override
        public int getItemCount() {
            return calificaciones.size();
        }

        static class CalificacionViewHolder extends RecyclerView.ViewHolder {
            TextView textViewLine1;
            TextView textViewLine2;

            CalificacionViewHolder(View itemView) {
                super(itemView);
                textViewLine1 = itemView.findViewById(android.R.id.text1);
                textViewLine2 = itemView.findViewById(android.R.id.text2);
            }
        }

}
