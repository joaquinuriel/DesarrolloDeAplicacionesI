package edu.uade.ritmofit.historial.Repository;

import java.util.List;

import edu.uade.ritmofit.historial.Model.Reserva;
import edu.uade.ritmofit.historial.Model.ReservaDetail;

public interface InterfaceRepositoryHistorial {




    void getHistorial(ReservasListCallback callback);

    void getReserva(String id, ReservaDetailCallback callback);

    interface ReservasListCallback {
        void onSuccess(List<Reserva> reservasList);
        void onError(String errorMessage);
    }

    interface ReservaDetailCallback {
        void onSuccess(ReservaDetail reservaDetail);
        void onError(String errorMessage);
    }



}
