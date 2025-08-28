package edu.uade.ritmofit.service;

import java.util.List;

import edu.uade.ritmofit.model.Turno;
import edu.uade.ritmofit.repository.TurnoRepository;

public class TurnoService {
    private TurnoRepository repo;

    public TurnoService(){
        this.repo = new TurnoRepository();
    }

    public List<Turno> getAllTurn(){
        return repo.getAllTurns();
    }
}
