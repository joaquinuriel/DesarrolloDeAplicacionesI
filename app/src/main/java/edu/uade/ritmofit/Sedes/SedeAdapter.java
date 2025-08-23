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

    private List<SedeDto> sedeList;
    private List<SedeDto> filteredSedeList; // Lista filtrada

    public SedeAdapter(List<SedeDto> sedeList) {
        this.sedeList = new ArrayList<>(sedeList); // Copia original
        this.filteredSedeList = new ArrayList<>(sedeList); // Lista inicial igual a la original
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
        holder.tvSedeId.setText("ID: " + sede.getId_sede());
    }

    @Override
    public int getItemCount() {
        return filteredSedeList != null ? filteredSedeList.size() : 0;
    }

    public void updateData(List<SedeDto> newSedeList) {
        this.sedeList = new ArrayList<>(newSedeList);
        this.filteredSedeList = new ArrayList<>(newSedeList);
        notifyDataSetChanged();
    }

    // Método para filtrar las sedes por nombre
    public void filter(String query) {
        filteredSedeList.clear();
        if (query == null || query.isEmpty()) {
            filteredSedeList.addAll(sedeList);
        } else {
            String filterPattern = query.toLowerCase().trim();
            for (SedeDto sede : sedeList) {
                if (sede.getNombre().toLowerCase().contains(filterPattern)) {
                    filteredSedeList.add(sede);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class SedeViewHolder extends RecyclerView.ViewHolder {
        TextView tvSedeNombre, tvSedeUbicacion, tvSedeId;

        public SedeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSedeNombre = itemView.findViewById(R.id.tvSedeNombre);
            tvSedeUbicacion = itemView.findViewById(R.id.tvSedeUbicacion);
            tvSedeId = itemView.findViewById(R.id.tvSedeId);
        }
    }
}