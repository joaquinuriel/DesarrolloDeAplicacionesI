package edu.uade.ritmofit.di;



import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.uade.ritmofit.repository.SedeRepository;
import edu.uade.ritmofit.repository.SedeRetrofitRepository;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    @Singleton
    public abstract SedeRepository bindSedeRepository(SedeRetrofitRepository impl);
}