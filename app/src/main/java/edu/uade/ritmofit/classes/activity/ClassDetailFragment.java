package edu.uade.ritmofit.classes.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import edu.uade.ritmofit.R;

public class ClassDetailFragment extends Fragment {

    private TextView tvTitleDetail, tvMetaDetail, tvInstructorDetail, tvAddressDetail, tvEstado;
    private Button btnReservar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_detail, container, false);

        tvTitleDetail = view.findViewById(R.id.tvTitleDetail);
        tvMetaDetail = view.findViewById(R.id.tvMetaDetail);
        tvInstructorDetail = view.findViewById(R.id.tvInstructorDetail);
        tvAddressDetail = view.findViewById(R.id.tvAddressDetail);
        tvEstado = view.findViewById(R.id.tvEstado);
        btnReservar = view.findViewById(R.id.btnReservar);

        // Obtener argumentos directamente del Bundle
        Bundle args = getArguments();
        if (args == null) {
            Toast.makeText(requireContext(), "No se recibieron datos", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigateUp();
            return view;
        }

        // Extraer valores del Bundle con claves específicas
        String classId = args.getString("classId");
        String title = (classId != null && !classId.isEmpty()) ? "Clase " + classId : "Detalles";
        String disciplina = args.getString("disciplina");
        String fecha = args.getString("fecha");
        String hora = args.getString("hora");
        Double duracion = args.getDouble("duracion");
        String profesor = args.getString("profesor");
        String sede = args.getString("sede");
        String estado = args.getString("estado");

        tvTitleDetail.setText(disciplina != null ? disciplina : "Sin título");
        String formattedMeta = "Sin datos";
        if (fecha != null && hora != null && duracion != null) {
            formattedMeta = String.format("%s %s • %.0f min", fecha, hora, duracion);
        }
        tvMetaDetail.setText(formattedMeta);
        tvInstructorDetail.setText("Profesor: " + (profesor != null ? profesor : "Sin profesor"));
        tvAddressDetail.setText("Sede: " + (sede != null ? sede : "Sin sede"));
        tvEstado.setText("Estado: " + (estado != null ? estado : "Sin estado"));

        btnReservar.setOnClickListener(v -> {
            btnReservar.setEnabled(false);
            btnReservar.setText("Reservado ✔︎");
        });

        Button backButton = view.findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        }

        return view;
    }
}