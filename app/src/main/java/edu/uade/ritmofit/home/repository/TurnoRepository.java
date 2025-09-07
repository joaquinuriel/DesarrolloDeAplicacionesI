package edu.uade.ritmofit.home.repository;

import java.util.ArrayList;
import java.util.List;

import edu.uade.ritmofit.home.model.Turno;

public class TurnoRepository {
    private List<Turno> listaTurnos;

    public TurnoRepository() {
        this.listaTurnos = new ArrayList<>();
        listaTurnos.add(new Turno("Boxeo","Palermo","21-09"));
        listaTurnos.add(new Turno("Crossfit","Villa Urquiza","22-09"));
        listaTurnos.add(new Turno("Yoga","Caseros","10-10"));
        listaTurnos.add(new Turno("Salsa","Monserrat","25-09"));
    }

    public List<Turno> getAllTurns() {
        return listaTurnos;
    }
}
