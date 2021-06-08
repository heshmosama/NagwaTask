package com.ips.sdxapp.core.di


import com.example.nagwatask.domain.repositories.MainRepo
import com.example.nagwatask.domain.usecases.MainUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun getMainUseCase(mainRepo: MainRepo) : MainUseCase = MainUseCase(mainRepo)
}