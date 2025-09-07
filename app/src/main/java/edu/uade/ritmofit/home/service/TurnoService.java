package edu.uade.ritmofit.home.service;

import java.util.List;

import edu.uade.ritmofit.home.model.Turno;
import edu.uade.ritmofit.home.repository.TurnoRepository;

public class TurnoService {
    private TurnoRepository repo;

    public TurnoService(){
        this.repo = new TurnoRepository();
    }

    public List<Turno> getAllTurn(){
        return repo.getAllTurns();
    }
}
