package edu.uade.ritmofit.Modules;

import java.util.List;

import edu.uade.ritmofit.data.api.model.SedeResponse;
import retrofit2.Call;

public class ServicioSedes implements  InterfaceService{
    @Override
    public Call<List<SedeResponse>> getSedes(){
        System.out.println("Service is working!");
        return null;
    }

}
