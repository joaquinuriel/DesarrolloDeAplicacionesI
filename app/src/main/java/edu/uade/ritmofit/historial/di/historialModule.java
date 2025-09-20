package edu.uade.ritmofit.historial.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.uade.ritmofit.auth.TokenManager;
import edu.uade.ritmofit.historial.Repository.InterfaceRepositoryHistorial;
import edu.uade.ritmofit.historial.Repository.RepositoryHistorial;
import edu.uade.ritmofit.historial.Service.InterfaceHistorialService;

@Module
@InstallIn(SingletonComponent.class)
public class historialModule {

    @Provides
    @Singleton
    public InterfaceRepositoryHistorial providehistorialRepository (InterfaceHistorialService historialService, TokenManager tokenManager ){
        return new RepositoryHistorial(historialService, tokenManager);
    }
}
