package edu.uade.ritmofit.classes.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.uade.ritmofit.auth.TokenManager;
import edu.uade.ritmofit.classes.repository.ClassesRepository;
import edu.uade.ritmofit.classes.repository.InterfaceRepositoryClasses;
import edu.uade.ritmofit.classes.service.ClassApiService;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class ClassesModule {
    @Provides
    @Singleton
    public InterfaceRepositoryClasses provideClassesRepository(ClassesRepository imp) {
        return imp;
    }
}

