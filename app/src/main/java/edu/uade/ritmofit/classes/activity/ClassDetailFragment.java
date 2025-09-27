package edu.uade.ritmofit.classes.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.auth.TokenManager;
import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.classes.model.ReservaRequest;
import edu.uade.ritmofit.classes.service.ClassApiService;
import edu.uade.ritmofit.classes.service.ReservaApiService;
import edu.uade.ritmofit.classes.model.Reservas;
import edu.uade.ritmofit.historial.Model.ReservaDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;

@AndroidEntryPoint
public class ClassDetailFragment extends Fragment {

    private static final String ARG_CLASE = "arg_clase";
    private Clase clase;

    @Inject
    ReservaApiService reservaApiService;

    @Inject
    ClassApiService classApiService;

    @Inject
    TokenManager tokenManager;

    public static ClassDetailFragment newInstance(Clase clase) {
        ClassDetailFragment fragment = new ClassDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CLASE, clase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clase = (Clase) getArguments().getSerializable("arg_clase");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_detail, container, false);

        TextView tvTitleDetail = view.findViewById(R.id.tvTitleDetail);
        TextView tvMetaDetail = view.findViewById(R.id.tvMetaDetail);
        TextView tvInstructorDetail = view.findViewById(R.id.tvInstructorDetail);
        TextView tvAddressDetail = view.findViewById(R.id.tvAddressDetail);
        TextView tvEstado = view.findViewById(R.id.tvEstado);
        Button btnReservar = view.findViewById(R.id.btnReservar);




        if (clase != null) {
            tvTitleDetail.setText(clase.getDisciplina());
            tvMetaDetail.setText(clase.getFecha() + " " + clase.getHorarioInicio());
            tvInstructorDetail.setText("Profesor: " + clase.getProfesorNombre());
            tvAddressDetail.setText("Sede: " + clase.getSedeNombre());
            tvEstado.setText("Estado: " + clase.getEstado());

            btnReservar.setEnabled(false);
            btnReservar.setText("Verificando...");

            reservaApiService.getReservasByUsuario(tokenManager.getUserId())
                    .enqueue(new Callback<List<ReservaRequest>>() {
                        @Override
                        public void onResponse(Call<List<ReservaRequest>> call, Response<List<ReservaRequest>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<ReservaRequest> reservas = response.body();

                                boolean yaReservado = false;
                                for (ReservaRequest r : reservas) {
                                    if (r.getIdClase() != null && r.getIdClase().equals(clase.getIdClase())) {
                                        yaReservado = true;
                                        break;
                                    }
                                }

                                if (yaReservado) {
                                    btnReservar.setEnabled(false);
                                    btnReservar.setText("Ya reservado ✔︎");
                                } else if (clase.getCupo() <= 0) {
                                    btnReservar.setEnabled(false);
                                    btnReservar.setText("Sin cupo");
                                } else if (clase.getEstado().equals("CANCELADA") || clase.getEstado().equals("EXPIRADA")) {
                                    btnReservar.setEnabled(false);
                                    btnReservar.setText("No disponible");
                                }
                                else {
                                    btnReservar.setEnabled(true);
                                    btnReservar.setText("Reservar");
                                }
                            } else {
                                Log.e("RESERVAS", "Error HTTP: " + response.code() + " - " + response.
                            body());
                                btnReservar.setEnabled(false);
                                btnReservar.setText("Error");
                            }
                        }


                        @Override
                        public void onFailure(Call<List<ReservaRequest>> call, Throwable t) {
                            btnReservar.setEnabled(false);
                            btnReservar.setText("Error de red");
                        }
                    });
        }

        btnReservar.setOnClickListener(v -> {
            String userId = tokenManager.getUserId();
            ReservaRequest request = new ReservaRequest(clase.getIdClase(), userId);
            clase.setCupo(clase.getCupo() - 1);

            reservaApiService.reservarClase(request).enqueue(new retrofit2.Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        btnReservar.setEnabled(false);
                        btnReservar.setText("Reservado ✔︎");
                        Toast.makeText(requireContext(), "Reservas confirmada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Error al reservar: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }


                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(requireContext(), "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            classApiService.updateClase(clase.getIdClase(), clase).enqueue(new Callback<Clase>() {
                @Override
                public void onResponse(Call<Clase> call, Response<Clase> res) {
                    if (res.isSuccessful()) {
                        btnReservar.setEnabled(false);
                        btnReservar.setText("Reservado ✔︎");
                        Toast.makeText(getContext(), "Reservas confirmada", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Clase> call, Throwable t) {
                    Toast.makeText(getContext(), "Error al actualizar clase", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }
}
