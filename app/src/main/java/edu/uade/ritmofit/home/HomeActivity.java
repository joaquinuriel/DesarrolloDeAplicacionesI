package edu.uade.ritmofit.home;

import static edu.uade.ritmofit.data.api.ApiClient.createService;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.classes.activity.ClassesActivity;
import edu.uade.ritmofit.data.api.ApiClient;
import edu.uade.ritmofit.home.model.Novedad;
import edu.uade.ritmofit.home.model.Turno;
import edu.uade.ritmofit.home.service.NovedadesHome;
import edu.uade.ritmofit.home.service.TurnoService;
import edu.uade.ritmofit.profile.ui.ProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private ListView turnosListView;
    private TurnoService service;
    private NovedadesHome serviceNovedades;
    private ApiClient apiClient;
    private List<Turno> listaTurnos;
    private List<Novedad> listaNovedades;

    public void obtenerPromociones(){
        serviceNovedades = createService(NovedadesHome.class);
        Call <List<Novedad>> call = serviceNovedades.getNovedades();
        call.enqueue(new Callback<List<Novedad>>() {
            @Override
            public void onResponse(Call<List<Novedad>> call, Response<List<Novedad>> response) {
                if (response.isSuccessful()){
                    listaNovedades = response.body();
                    Log.e("Retrofit casero", "respuesta exitosa" );
                }else{
                    Log.e("Retrofit", "Error en la respuesta: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Novedad>> call, Throwable t) {
                Log.e("Retrofit aiiie", "Error en la llamada: " + t.getMessage());
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Cargar Novedades
        obtenerPromociones();

        Button btn1 = findViewById(R.id.button6);//Perfil

        Button btn2 = findViewById(R.id.button7);//Clases

        Button btn3 = findViewById(R.id.button8);//Historial todo

        btn1.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        btn2.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ClassesActivity.class);
            startActivity(intent);
        });


        service = new TurnoService();
        listaTurnos = service.getAllTurn();

        ArrayList<String> displayTurnos = new ArrayList<>();
        for (Turno turn : listaTurnos){
            displayTurnos.add(turn.getClase() + "-" + turn.getSede() + "-" + turn.getFecha());
        }

        turnosListView = findViewById(R.id.listViewTurnos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayTurnos
        );

        turnosListView.setAdapter(adapter);
/*
        turnosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        TODO COMPLETAR
        */
    }
}