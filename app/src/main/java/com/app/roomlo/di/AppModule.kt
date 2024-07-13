package com.app.roomlo.di

import android.content.Context
import com.app.roomlo.data.AuthRepository
import com.app.roomlo.data.PreferenceHelper
import com.app.roomlo.data.UserRepository
import com.app.roomlo.viewmodels.AuthViewModel
import com.app.roomlo.viewmodels.DatabaseViewModel
import com.app.roomlo.viewmodels.RoomViewModel
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
    fun provideAuthRepo(preferenceHelper: PreferenceHelper): AuthRepository {
        return AuthRepository(preferenceHelper)
    }



    @Provides
    @Singleton
    fun provideDatabaseVM(userRepository: UserRepository): DatabaseViewModel {
        return DatabaseViewModel(userRepository)
    }

    @Provides
    @Singleton
    fun provideSharedVM(userRepository: UserRepository): SharedViewModel {
        return SharedViewModel(userRepository)
    }

    @Provides
    @Singleton
    fun provideAuthVM(
        sharedViewModel: SharedViewModel,
        dbViewModel: DatabaseViewModel,
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
    fun provideRoomVM(): RoomViewModel {
        return RoomViewModel()
    }
}
