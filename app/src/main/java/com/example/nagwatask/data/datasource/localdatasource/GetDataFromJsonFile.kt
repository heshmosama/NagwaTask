package com.example.nagwatask.data.datasource.localdatasource

import android.provider.SyncStateContract
import com.example.nagwatask.core.Constants.Constants
import com.example.nagwatask.data.Models.FilesList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetDataFromJsonFile @Inject constructor() {

     suspend fun getFilesList() : FilesList {
         val gson = Gson()
         val filesList = object : TypeToken<FilesList>(){}.type
         return gson.fromJson(Constants.jsonList,filesList)
     }


}