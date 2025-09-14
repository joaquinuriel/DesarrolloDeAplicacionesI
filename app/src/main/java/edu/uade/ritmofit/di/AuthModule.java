package edu.uade.ritmofit.di;



import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.uade.ritmofit.auth.repository.AuthApiService;
import edu.uade.ritmofit.auth.RetrofitClient;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class AuthModule {

    @Provides
    @Singleton
    public AuthApiService provideAuthApiService() {
        return RetrofitClient.getAuthApiService();
    }
}