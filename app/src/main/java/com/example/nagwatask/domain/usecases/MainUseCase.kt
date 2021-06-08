package com.example.nagwatask.domain.usecases

import com.example.nagwatask.core.utils.DataState
import com.example.nagwatask.domain.entities.FilesListEntity
import com.example.nagwatask.domain.entities.FilesListItemEntity
import com.example.nagwatask.domain.repositories.MainRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainUseCase constructor(private val mainRepo: MainRepo) {

    suspend fun getFilesList(): Flow<DataState<FilesListEntity>> = flow {
        emit(DataState.Loading)
        try {
            val response = mainRepo.getFilesList()
            val entity = FilesListEntity()

            response.forEach {
                entity.add(
                    FilesListItemEntity(
                        id = it.id,
                        type = it.type,
                        name = it.name,
                        url = it.url
                    ))
            }

            emit(DataState.Success(entity))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }
}