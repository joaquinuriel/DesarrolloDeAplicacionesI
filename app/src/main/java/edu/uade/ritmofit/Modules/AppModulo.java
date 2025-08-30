package edu.uade.ritmofit.Modules;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class AppModulo {

    @Provides
    @Singleton
    public InterfaceService ServiceSedes() {
        return new ServicioSedes();
    }
}