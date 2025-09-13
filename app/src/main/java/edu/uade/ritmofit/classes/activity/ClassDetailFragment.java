package edu.uade.ritmofit.classes.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

        // Obtener argumentos pasados desde ClassesFragment
        ClassDetailFragmentArgs args = ClassDetailFragmentArgs.fromBundle(getArguments());
        String title = args.getClassId() > 0 ? "Clase " + args.getClassId() : "Detalles";
        String disciplina = args.getDisciplina();
        String fecha = args.getFecha();
        String hora = args.getHora();
        Double duracion = args.getDuracion();
        String profesor = args.getProfesor();
        String sede = args.getSede();
        String estado = args.getEstado();

        tvTitleDetail.setText(disciplina != null ? disciplina : "Sin título");
        tvMetaDetail.setText(String.format("%s %s • %.0f min", fecha, hora, duracion));
        tvInstructorDetail.setText("Profesor: " + (profesor != null ? profesor : "Sin profesor"));
        tvAddressDetail.setText("Sede: " + (sede != null ? sede : "Sin sede"));
        tvEstado.setText("Estado: " + (estado != null ? estado : "Sin estado"));

        btnReservar.setOnClickListener(v -> {
            btnReservar.setEnabled(false);
            btnReservar.setText("Reservado ✔︎");
        });

        // Botón de regreso
        Button backButton = view.findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        }

        return view;
    }
}