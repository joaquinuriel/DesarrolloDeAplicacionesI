package edu.uade.ritmofit.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.uade.ritmofit.Sedes.Service.ApiService;
import edu.uade.ritmofit.Sedes.Service.InterfaceService;
import edu.uade.ritmofit.auth.AuthInterceptor;
import edu.uade.ritmofit.auth.TokenManager;
import edu.uade.ritmofit.auth.repository.AuthApiService;
import edu.uade.ritmofit.historial.Repository.InterfaceRepositoryHistorial;
import edu.uade.ritmofit.historial.Repository.RepositoryHistorial;
import edu.uade.ritmofit.historial.Service.InterfaceHistorialService;
import edu.uade.ritmofit.home.service.HomeService;
import edu.uade.ritmofit.profile.data.service.ProfileService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Provides
    @Singleton
    public TokenManager provideTokenManager(@ApplicationContext Context context) {
        return new TokenManager(context);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(TokenManager tokenManager) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(tokenManager))
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9090/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public InterfaceService provideInterfaceService(Retrofit retrofit) {
        return retrofit.create(InterfaceService.class);
    }

    @Provides
    @Singleton
    public AuthApiService provideAuthApiService(Retrofit retrofit) {
        return retrofit.create(AuthApiService.class);
    }
    @Provides
    @Singleton
    public ApiService provideApiService(InterfaceService interfaceService) {
        return new ApiService(interfaceService);
    }

    @Provides
    @Singleton
    public InterfaceRepositoryHistorial providehistorialRepository (InterfaceHistorialService historialService, TokenManager tokenManager ){
        return new RepositoryHistorial(historialService, tokenManager);
    }
    @Provides
    public InterfaceHistorialService provideHistorialService(Retrofit retrofit) {
        return retrofit.create(InterfaceHistorialService.class);

    }

    @Provides
    @Singleton
    public HomeService provideHomeService(Retrofit retrofit) {
        return retrofit.create(HomeService.class);
    }

    @Provides
    @Singleton
    public ProfileService provideProfileService(Retrofit retrofit) {
        return retrofit.create(ProfileService.class);
    }
}