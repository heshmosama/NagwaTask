package com.ips.sdxapp.core.di


import com.example.nagwatask.data.datasource.localdatasource.GetDataFromJsonFile
import com.example.nagwatask.data.repositories.MainRepoImpl
import com.example.nagwatask.domain.repositories.MainRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun getMainRepo(networkService: HttpClient, localData: GetDataFromJsonFile) : MainRepo = MainRepoImpl(networkService,localData)
}