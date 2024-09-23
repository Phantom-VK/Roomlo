package com.app.roomlo.di

import android.content.Context
import com.app.roomlo.repository.AuthRepository
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.repository.PropertyRepository
import com.app.roomlo.repository.UserRepository
import com.app.roomlo.viewmodels.AuthViewModel
import com.app.roomlo.viewmodels.LocationViewModel
import com.app.roomlo.viewmodels.UserViewModel
import com.app.roomlo.viewmodels.PropertyViewModel
import com.app.roomlo.viewmodels.SharedViewModel
import com.app.roomlo.viewmodels.UserProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): PreferenceHelper {
        return PreferenceHelper(context)
    }

    @Provides
    @Singleton
    fun provideUserRepo(preferenceHelper: PreferenceHelper): UserRepository {
        return UserRepository(preferenceHelper)
    }

    @Provides
    @Singleton
    fun providePropertyRepo(preferenceHelper: PreferenceHelper):PropertyRepository{
        return PropertyRepository(preferenceHelper)
    }


    @Provides
    @Singleton
    fun provideAuthRepo(preferenceHelper: PreferenceHelper, sharedViewModel: SharedViewModel): AuthRepository {
        return AuthRepository(preferenceHelper, sharedViewModel)
    }



    @Provides
    @Singleton
    fun provideDatabaseVM(userRepository: UserRepository,propertyRepository:PropertyRepository ): UserViewModel {
        return UserViewModel(userRepository, propertyRepository)
    }

    @Provides
    @Singleton
    fun provideSharedVM(userRepository: UserRepository, propertyRepository:PropertyRepository): SharedViewModel {
        return SharedViewModel(userRepository, propertyRepository)
    }

    @Provides
    @Singleton
    fun provideAuthVM(
        sharedViewModel: SharedViewModel,
        dbViewModel: UserViewModel,
        preferenceHelper: PreferenceHelper,
        authRepository: AuthRepository
    ): AuthViewModel {
        return AuthViewModel(
            dbViewModel = dbViewModel,
            sharedViewModel = sharedViewModel,
            preferenceHelper = preferenceHelper,
            authRepository = authRepository
        )
    }

    @Provides
    @Singleton
    fun provideUserProfileVM(userRepository: UserRepository): UserProfileViewModel {
        return UserProfileViewModel(userRepository)
    }

    @Provides
    @Singleton
    fun providePropertyVM(propertyRepository: PropertyRepository): PropertyViewModel {
        return PropertyViewModel(propertyRepository)
    }

    @Provides
    @Singleton
    fun provideLocationVM():LocationViewModel{
        return LocationViewModel()
    }
}
