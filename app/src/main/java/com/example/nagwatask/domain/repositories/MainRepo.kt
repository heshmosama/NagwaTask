package com.example.nagwatask.domain.repositories

import com.example.nagwatask.core.utils.DownloadResult
import com.example.nagwatask.data.Models.FilesList
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

interface MainRepo{

    suspend fun getFilesList() : FilesList
    suspend fun downloadFile(file: File, url: Url): Flow<DownloadResult>

}