package edu.uade.ritmofit.profile.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.uade.ritmofit.profile.data.repository.ProfileRepository;
import edu.uade.ritmofit.profile.data.repository.ProfileRepositoryImpl;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class ProfileModule {
    @Provides
    @Singleton
    public ProfileRepository provideProfileRepository(ProfileRepositoryImpl impl) {
        return impl;
    }
}