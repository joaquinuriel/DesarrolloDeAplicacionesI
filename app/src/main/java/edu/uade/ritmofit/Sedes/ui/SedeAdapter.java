package edu.uade.ritmofit.Sedes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.Sedes.Model.SedeDto; // Cambiado de SedeResponse a SedeDto

import java.util.ArrayList;
import java.util.List;

public class SedeAdapter extends RecyclerView.Adapter<SedeAdapter.SedeViewHolder> {

    private List<SedeDto> sedeList;         // Lista original de sedes (cambiada a SedeDto)
    private List<SedeDto> filteredSedeList; // Lista filtrada para mostrar (cambiada a SedeDto)
    private OnItemClickListener listener;   // Callback para clics

    /**
     * Interfaz para manejar clics en los elementos.
     */
    public interface OnItemClickListener {
        void onItemClick(SedeDto sede); // Cambiado a SedeDto
    }

    /**
     * Constructor que inicializa las listas de sedes y el listener.
     * @param listener Callback para manejar clics (puede ser null)
     */
    public SedeAdapter(OnItemClickListener listener) {
        this.listener = listener;
        this.sedeList = new ArrayList<>();
        this.filteredSedeList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SedeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sede_card_layout, parent, false);
        return new SedeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SedeViewHolder holder, int position) {
        SedeDto sede = filteredSedeList.get(position); // Cambiado a SedeDto
        holder.tvSedeNombre.setText(sede.getNombre());
        holder.tvSedeUbicacion.setText(sede.getUbicacion());
        holder.tvSedeBarrio.setText(sede.getBarrio());

        // Configurar clic en el item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(sede);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredSedeList != null ? filteredSedeList.size() : 0;
    }

    /**
     * Actualiza los datos del adaptador con una nueva lista de sedes.
     * @param newSedeList Nueva lista de sedes (puede ser null)
     */
    public void updateData(List<SedeDto> newSedeList) { // Cambiado a List<SedeDto>
        this.sedeList.clear();
        this.filteredSedeList.clear();
        if (newSedeList != null) {
            this.sedeList.addAll(newSedeList);
            this.filteredSedeList.addAll(newSedeList);
        }
        notifyDataSetChanged();
    }

    /**
     * Filtra las sedes según un criterio de búsqueda.
     * @param query Texto de búsqueda (puede ser null o vacío)
     */
    public void filter(String query) {
        filteredSedeList.clear();
        if (query == null || query.trim().isEmpty()) {
            filteredSedeList.addAll(sedeList);
        } else {
            String filterPattern = query.toLowerCase().trim();
            for (SedeDto sede : sedeList) { // Cambiado a SedeDto
                if (sede != null) {
                    boolean matches = false;
                    if (sede.getNombre() != null && sede.getNombre().toLowerCase().contains(filterPattern)) {
                        matches = true;
                    }
                    if (sede.getBarrio() != null && sede.getBarrio().toLowerCase().contains(filterPattern)) {
                        matches = true;
                    }
                    if (matches) {
                        filteredSedeList.add(sede);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * ViewHolder para los elementos de la lista de sedes.
     */
    static class SedeViewHolder extends RecyclerView.ViewHolder {
        TextView tvSedeNombre, tvSedeUbicacion, tvSedeBarrio;

        public SedeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSedeNombre = itemView.findViewById(R.id.tvSedeNombre);
            tvSedeUbicacion = itemView.findViewById(R.id.tvSedeUbicacion);
            tvSedeBarrio = itemView.findViewById(R.id.tvSedeBarrio);
        }
    }
}