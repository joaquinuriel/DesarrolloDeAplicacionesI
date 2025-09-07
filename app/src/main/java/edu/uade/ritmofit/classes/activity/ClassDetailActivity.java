package edu.uade.ritmofit.classes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.classes.model.Class;

public class ClassDetailActivity extends AppCompatActivity {

    private static final String EXTRA_TITLE = "extra_title";
    private static final String EXTRA_DISCIPLINA = "extra_disciplina";
    private static final String EXTRA_FECHA = "extra_fecha";
    private static final String EXTRA_HORA = "extra_hora";
    private static final String EXTRA_DURACION = "extra_duracion";
    private static final String EXTRA_PROFESOR = "extra_profesor";
    private static final String EXTRA_SEDE = "extra_sede";
    private static final String EXTRA_CUPO = "extra_cupo";
    private static final String EXTRA_ESTADO = "extra_estado";

    public static void start(Context context, Class clase) {
        Intent i = new Intent(context, ClassDetailActivity.class);
        i.putExtra(EXTRA_TITLE, clase.getIdClase());
        i.putExtra(EXTRA_DISCIPLINA, clase.getDisciplina());
        i.putExtra(EXTRA_FECHA, clase.getFecha());
        i.putExtra(EXTRA_HORA, clase.getHorarioInicio());
        i.putExtra(EXTRA_DURACION, clase.getDuracion());
        i.putExtra(EXTRA_PROFESOR, clase.getIdProfesor());
        i.putExtra(EXTRA_SEDE, clase.getIdSede());
        i.putExtra(EXTRA_CUPO, clase.getCupo());
        i.putExtra(EXTRA_ESTADO, clase.getEstado());
        context.startActivity(i);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);


        TextView tvTitleDetail = findViewById(R.id.tvTitleDetail);
        TextView tvMetaDetail = findViewById(R.id.tvMetaDetail);
        TextView tvInstructorDetail = findViewById(R.id.tvInstructorDetail);
        TextView tvAddressDetail = findViewById(R.id.tvAddressDetail);
        TextView tvEstado = findViewById(R.id.tvEstado);
        Button btnReservar = findViewById(R.id.btnReservar);

        Intent i = getIntent();
        tvTitleDetail.setText(i.getStringExtra(EXTRA_DISCIPLINA));
        tvMetaDetail.setText(i.getStringExtra(EXTRA_FECHA) + " " + i.getStringExtra(EXTRA_HORA)
                + " • " + i.getDoubleExtra(EXTRA_DURACION, 0) + " min");
        tvInstructorDetail.setText("Profesor: " + i.getStringExtra(EXTRA_PROFESOR));
        tvAddressDetail.setText("Sede: " + i.getStringExtra(EXTRA_SEDE));
        tvEstado.setText("Estado: " + i.getStringExtra(EXTRA_ESTADO));

        btnReservar.setOnClickListener(v -> {
            btnReservar.setEnabled(false);
            btnReservar.setText("Reservado ✔︎");
        });
    }
}
