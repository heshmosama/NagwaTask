package com.example.nagwatask.domain.repositories

import com.example.nagwatask.data.Models.FilesList
import javax.inject.Inject

interface MainRepo{

    suspend fun getFilesList() : FilesList
    suspend fun downloadFile()

}