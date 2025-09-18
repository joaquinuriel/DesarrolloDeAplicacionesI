package edu.uade.ritmofit.Sedes.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.uade.ritmofit.Sedes.Repository.SedeRepository;
import edu.uade.ritmofit.Sedes.Repository.SedeRetrofitRepository;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract SedeRepository bindSedeRepository(SedeRetrofitRepository impl);
}