package edu.uade.ritmofit.auth;



import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
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