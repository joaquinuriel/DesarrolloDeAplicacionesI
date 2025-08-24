package edu.uade.ritmofit.Sedes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.Sedes.Model.SedeDto;
import java.util.ArrayList;
import java.util.List;

public class SedeAdapter extends RecyclerView.Adapter<SedeAdapter.SedeViewHolder> {

    private List<SedeDto> sedeList;         // Lista original de sedes
    private List<SedeDto> filteredSedeList; // Lista filtrada para mostrar

    /**
     * Constructor que inicializa las listas de sedes.
     * Si se pasa null, se crean listas vacías para evitar excepciones.
     * @param sedeList Lista inicial de sedes (puede ser null)
     */
    public SedeAdapter(List<SedeDto> sedeList) {
        this.sedeList = (sedeList != null) ? new ArrayList<>(sedeList) : new ArrayList<>();
        this.filteredSedeList = new ArrayList<>(this.sedeList);
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
        SedeDto sede = filteredSedeList.get(position);
        holder.tvSedeNombre.setText(sede.getNombre());
        holder.tvSedeUbicacion.setText(sede.getUbicacion());
        holder.tvSedeBarrio.setText(sede.getBarrio());
    }

    @Override
    public int getItemCount() {
        return filteredSedeList != null ? filteredSedeList.size() : 0;
    }

    /**
     * Actualiza los datos del adaptador con una nueva lista de sedes.
     * @param newSedeList Nueva lista de sedes (puede ser null)
     */
    public void updateData(List<SedeDto> newSedeList) {
        this.sedeList = (newSedeList != null) ? new ArrayList<>(newSedeList) : new ArrayList<>();
        this.filteredSedeList = new ArrayList<>(this.sedeList);
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
            for (SedeDto sede : sedeList) {
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
        TextView tvSedeNombre, tvSedeUbicacion,tvSedeBarrio;

        public SedeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSedeNombre = itemView.findViewById(R.id.tvSedeNombre);
            tvSedeUbicacion = itemView.findViewById(R.id.tvSedeUbicacion);
            tvSedeBarrio=itemView.findViewById(R.id.tvSedeBarrio);
        }
    }
}