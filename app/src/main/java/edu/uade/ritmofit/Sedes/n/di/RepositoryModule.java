package edu.uade.ritmofit.Sedes.n.di;



import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.uade.ritmofit.Sedes.n.Repository.SedeRepository;
import edu.uade.ritmofit.Sedes.n.Repository.SedeRetrofitRepository;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    @Singleton
    public abstract SedeRepository bindSedeRepository(SedeRetrofitRepository impl);
}