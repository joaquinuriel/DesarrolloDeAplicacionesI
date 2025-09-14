package edu.uade.ritmofit.Sedes.Service;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import edu.uade.ritmofit.Sedes.Model.SedeDto;
import edu.uade.ritmofit.Sedes.Model.SedeResponse;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.Request;

public class ApiService {
    private static final String TAG = "ApiService";
    private final InterfaceService interfaceService;

    @Inject
    public ApiService(InterfaceService interfaceService) {
        this.interfaceService = interfaceService;
    }

    public Call<List<SedeDto>> getAllSedes() {
        return new SedeListCall(interfaceService.getAllSedes());
    }

    public Call<SedeDto> getSedeById(String id) {
        return new SedeCall(interfaceService.getSedeById(id));
    }

    private List<SedeDto> convertToSedeDto(List<SedeResponse> sedeResponses) {
        List<SedeDto> sedes = new ArrayList<>();
        if (sedeResponses == null) {
            return sedes;
        }
        for (SedeResponse response : sedeResponses) {
            if (response != null) {
                SedeDto sede = new SedeDto(
                        response.getId_sede(),
                        response.getNombre(),
                        response.getUbicacion(),
                        response.getBarrio()
                );
                sedes.add(sede);
                Log.d(TAG, "Sede convertida: " + sede.toString());
            }
        }
        return sedes;
    }

    private SedeDto convertToSedeDto(SedeResponse response) {
        if (response == null) {
            return null;
        }
        return new SedeDto(
                response.getId_sede(),
                response.getNombre(),
                response.getUbicacion(),
                response.getBarrio()
        );
    }

    private class SedeListCall implements Call<List<SedeDto>> {
        private final Call<List<SedeResponse>> call;

        SedeListCall(Call<List<SedeResponse>> call) {
            this.call = call;
        }

        @Override
        public Response<List<SedeDto>> execute() throws IOException {
            Response<List<SedeResponse>> response = call.execute();
            if (response.isSuccessful()) {
                List<SedeResponse> sedeResponses = response.body();
                List<SedeDto> sedeDtos = (sedeResponses != null) ? convertToSedeDto(sedeResponses) : Collections.emptyList();
                return Response.success(sedeDtos);
            }
            return Response.error(response.code(), response.errorBody());
        }

        @Override
        public void enqueue(final Callback<List<SedeDto>> callback) {
            call.enqueue(new Callback<List<SedeResponse>>() {
                @Override
                public void onResponse(Call<List<SedeResponse>> innerCall, Response<List<SedeResponse>> response) {
                    if (response.isSuccessful()) {
                        List<SedeResponse> sedeResponses = response.body();
                        List<SedeDto> sedeDtos = (sedeResponses != null) ? convertToSedeDto(sedeResponses) : Collections.emptyList();
                        callback.onResponse(SedeListCall.this, Response.success(sedeDtos));
                    } else {
                        callback.onResponse(SedeListCall.this, Response.error(response.code(), response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<List<SedeResponse>> innerCall, Throwable t) {
                    callback.onFailure(SedeListCall.this, t);
                }
            });
        }

        @Override
        public boolean isExecuted() { return call.isExecuted(); }
        @Override
        public void cancel() { call.cancel(); }
        @Override
        public boolean isCanceled() { return call.isCanceled(); }
        @Override
        public Call<List<SedeDto>> clone() { return new SedeListCall(call.clone()); }
        @Override
        public Request request() { return call.request(); }
        @Override
        public Timeout timeout() { return call.timeout(); }
    }

    private class SedeCall implements Call<SedeDto> {
        private final Call<SedeResponse> call;

        SedeCall(Call<SedeResponse> call) {
            this.call = call;
        }

        @Override
        public Response<SedeDto> execute() throws IOException {
            Response<SedeResponse> response = call.execute();
            if (response.isSuccessful()) {
                SedeResponse sedeResponse = response.body();
                SedeDto sedeDto = (sedeResponse != null) ? convertToSedeDto(sedeResponse) : null;
                return Response.success(sedeDto);
            }
            return Response.error(response.code(), response.errorBody());
        }

        @Override
        public void enqueue(final Callback<SedeDto> callback) {
            call.enqueue(new Callback<SedeResponse>() {
                @Override
                public void onResponse(Call<SedeResponse> innerCall, Response<SedeResponse> response) {
                    if (response.isSuccessful()) {
                        SedeResponse sedeResponse = response.body();
                        SedeDto sedeDto = (sedeResponse != null) ? convertToSedeDto(sedeResponse) : null;
                        callback.onResponse(SedeCall.this, Response.success(sedeDto));
                    } else {
                        callback.onResponse(SedeCall.this, Response.error(response.code(), response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<SedeResponse> innerCall, Throwable t) {
                    callback.onFailure(SedeCall.this, t);
                }
            });
        }

        @Override
        public boolean isExecuted() { return call.isExecuted(); }
        @Override
        public void cancel() { call.cancel(); }
        @Override
        public boolean isCanceled() { return call.isCanceled(); }
        @Override
        public Call<SedeDto> clone() { return new SedeCall(call.clone()); }
        @Override
        public Request request() { return call.request(); }
        @Override
        public Timeout timeout() { return call.timeout(); }
    }
}