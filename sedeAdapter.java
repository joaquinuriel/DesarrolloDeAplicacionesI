package edu.uade.ritmofit.Sedes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.Sedes.Model.SedeDto;
import java.util.List;

public class SedeAdapter extends RecyclerView.Adapter<SedeAdapter.SedeViewHolder> {

    private List<SedeDto> sedeList;

    public SedeAdapter(List<SedeDto> sedeList) {
        this.sedeList = sedeList;
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
        SedeDto sede = sedeList.get(position);
        holder.tvSedeNombre.setText(sede.getNombre());
        holder.tvSedeUbicacion.setText(sede.getUbicacion());
        holder.tvSedeId.setText("ID: " + sede.getId_sede());
    }

    @Override
    public int getItemCount() {
        return sedeList != null ? sedeList.size() : 0;
    }

    public void updateData(List<SedeDto> newSedeList) {
        this.sedeList = newSedeList;
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