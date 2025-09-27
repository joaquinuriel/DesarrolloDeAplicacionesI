package edu.uade.ritmofit.classes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.uade.ritmofit.Sedes.Model.SedeDto;
import edu.uade.ritmofit.Sedes.Repository.SedeRepository;
import edu.uade.ritmofit.classes.model.Clase;
import edu.uade.ritmofit.classes.model.Sede;
import edu.uade.ritmofit.classes.model.Profesor;
import edu.uade.ritmofit.classes.repository.InterfaceRepositoryClasses;
import edu.uade.ritmofit.classes.service.ProfesorApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class ClassesViewModel extends ViewModel {
    private final InterfaceRepositoryClasses repository;
    private final SedeRepository sedeRepository;
    private final ProfesorApiService profesorApi;

    private final MutableLiveData<List<Clase>> clases = new MutableLiveData<>();
    private final MutableLiveData<List<Clase>> clasesOriginales = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();

    @Inject
    public ClassesViewModel(InterfaceRepositoryClasses repository,
                            SedeRepository sedeRepository,
                            ProfesorApiService profesorApi) {
        this.repository = repository;
        this.sedeRepository = sedeRepository;
        this.profesorApi = profesorApi;
    }

    public LiveData<List<Clase>> getClases() { return clases; }
    public LiveData<String> getMensaje() { return mensaje; }

    public void cargarClases() {
        repository.getClases().enqueue(new Callback<List<Clase>>() {
            @Override
            public void onResponse(Call<List<Clase>> call, Response<List<Clase>> res) {
                if (!res.isSuccessful() || res.body() == null) {
                    mensaje.postValue("Error cargando clases.");
                    clases.postValue(Collections.emptyList());
                    return;
                }
                List<Clase> raw = res.body();
                clasesOriginales.setValue(res.body());

                Map<String, String> sedeNames = new HashMap<>();
                Map<String, String> profNames = new HashMap<>();
                AtomicInteger pending = new AtomicInteger(0);

                for (Clase c : raw) {
                    if (c.getIdSede() != null && !sedeNames.containsKey(c.getIdSede())) {
                        pending.incrementAndGet();
                        sedeRepository.getSedeById(c.getIdSede()).enqueue(new Callback<SedeDto>() {
                            @Override
                            public void onResponse(Call<SedeDto> call, Response<SedeDto> rs) {
                                String name = (rs.isSuccessful() && rs.body() != null)
                                        ? rs.body().getNombre() + (rs.body().getBarrio() != null ? " - " + rs.body().getBarrio() : "")
                                        : c.getIdSede();
                                sedeNames.put(c.getIdSede(), name);
                                if (pending.decrementAndGet() == 0) {
                                    finishLoading(raw, sedeNames, profNames);
                                }
                            }

                            @Override
                            public void onFailure(Call<SedeDto> call, Throwable t) {
                                sedeNames.put(c.getIdSede(), c.getIdSede());
                                if (pending.decrementAndGet() == 0) {
                                    finishLoading(raw, sedeNames, profNames);
                                }
                            }
                        });

                    }

                    if (c.getIdProfesor() != null && !profNames.containsKey(c.getIdProfesor())) {
                        pending.incrementAndGet();
                        profesorApi.getProfesorById(c.getIdProfesor()).enqueue(new Callback<Profesor>() {
                            @Override
                            public void onResponse(Call<Profesor> call, Response<Profesor> rp) {
                                String name = (rp.isSuccessful() && rp.body() != null)
                                        ? rp.body().getNombre()
                                        : c.getIdProfesor();
                                profNames.put(c.getIdProfesor(), name);
                                if (pending.decrementAndGet() == 0) {
                                    finishLoading(raw, sedeNames, profNames);
                                }
                            }

                            @Override
                            public void onFailure(Call<Profesor> call, Throwable t) {
                                profNames.put(c.getIdProfesor(), c.getIdProfesor());
                                if (pending.decrementAndGet() == 0) {
                                    finishLoading(raw, sedeNames, profNames);
                                }
                            }
                        });
                    }
                }


                if (pending.get() == 0) {
                    finishLoading(raw, sedeNames, profNames);
                }
            }

            @Override
            public void onFailure(Call<List<Clase>> call, Throwable t) {
                mensaje.postValue("Error de conexión con el servidor.");
                clases.postValue(Collections.emptyList());
            }
        });
    }

    private void finishLoading(List<Clase> raw, Map<String, String> sedeNames, Map<String, String> profNames) {
        for (Clase c : raw) {
            if (c.getIdSede() != null) {
                c.setSedeNombre(sedeNames.getOrDefault(c.getIdSede(), c.getIdSede()));
            }
            if (c.getIdProfesor() != null) {
                c.setProfesorNombre(profNames.getOrDefault(c.getIdProfesor(), c.getIdProfesor()));
            }
        }
        clases.postValue(raw);
    }



    public void filtrar(String sede, String disciplina, String fecha) {
        List<Clase> todas = clasesOriginales.getValue();
        if (todas == null) return;

        List<Clase> filtradas = new ArrayList<>();
        for (Clase c : todas) {
            boolean sedeOk = (sede == null || sede.equals("Todos") || sede.equals(c.getSedeNombre()));
            boolean discOk = (disciplina == null || disciplina.equals("Todos") || disciplina.equals(c.getDisciplina()));
            boolean fechaOk = (fecha == null || fecha.equals(c.getFecha()));

            if (sedeOk && discOk && fechaOk) {
                filtradas.add(c);
            }
        }

        clases.setValue(filtradas);
    }


}
