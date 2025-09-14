package edu.uade.ritmofit.Sedes.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.uade.ritmofit.Sedes.Service.ApiService;
import edu.uade.ritmofit.Sedes.Service.InterfaceService;
import edu.uade.ritmofit.auth.AuthInterceptor;
import edu.uade.ritmofit.auth.repository.AuthRepository;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(AuthRepository authRepository) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(authRepository))
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9090/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public InterfaceService provideInterfaceService(Retrofit retrofit) {
        return retrofit.create(InterfaceService.class);
    }

    @Provides
    @Singleton
    public ApiService provideApiService(InterfaceService interfaceService) {
        return new ApiService(interfaceService);
    }
}