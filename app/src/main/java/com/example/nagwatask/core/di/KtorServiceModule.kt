package com.ips.sdxapp.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object KtorServiceModule {
    @Singleton
    @Provides
    fun getMainRepo() = HttpClient()
}