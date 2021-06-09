package com.example.nagwatask.data.repositories

import com.example.nagwatask.core.utils.DownloadResult
import com.example.nagwatask.data.Models.FilesList
import com.example.nagwatask.data.datasource.RemoteDatasource.downloadFile
import com.example.nagwatask.data.datasource.localdatasource.GetDataFromJsonFile
import com.example.nagwatask.domain.repositories.MainRepo
import io.ktor.client.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepoImpl @Inject constructor(
    val networkService: HttpClient, val localData: GetDataFromJsonFile
) : MainRepo {
    override suspend fun getFilesList(): FilesList {
       return localData.getFilesList()
    }

    override suspend fun downloadFile(file: File, url: Url): Flow<DownloadResult> = networkService.downloadFile(file,url)
}