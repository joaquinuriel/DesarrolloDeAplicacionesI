package edu.uade.ritmofit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.uade.ritmofit.model.Turno;
import edu.uade.ritmofit.service.TurnoService;


public class HomeActivity extends AppCompatActivity {
    private ListView turnosListView;
    private TurnoService service;
    private List<Turno> listaTurnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        service = new TurnoService();
        listaTurnos = service.getAllTurn();
        /*TODO falta incializar la lista de objetos*/

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
    }
}