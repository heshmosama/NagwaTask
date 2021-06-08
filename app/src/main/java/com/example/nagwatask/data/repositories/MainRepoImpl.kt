package com.example.nagwatask.data.repositories

import com.example.nagwatask.data.Models.FilesList
import com.example.nagwatask.data.datasource.RemoteDatasource.NetworkService
import com.example.nagwatask.data.datasource.localdatasource.GetDataFromJsonFile
import com.example.nagwatask.domain.repositories.MainRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepoImpl @Inject constructor(
   val networkService: NetworkService,val localData: GetDataFromJsonFile
) : MainRepo {
    override suspend fun getFilesList(): FilesList {
       return localData.getFilesList()
    }

    override suspend fun downloadFile() {
        TODO("Not yet implemented")
    }
}