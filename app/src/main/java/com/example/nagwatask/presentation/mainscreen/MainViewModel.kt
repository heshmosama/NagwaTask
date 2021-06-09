package com.example.nagwatask.presentation.mainscreen

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.nagwatask.core.utils.DataState
import com.example.nagwatask.core.utils.DownloadResult
import com.example.nagwatask.domain.entities.FilesListEntity
import com.example.nagwatask.domain.usecases.MainUseCase
import io.ktor.http.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

@ExperimentalCoroutinesApi
class MainViewModel  @ViewModelInject constructor(
    private val mainUseCase: MainUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _data : MutableStateFlow<FilesListEntity> = MutableStateFlow(FilesListEntity())
    var data: StateFlow<FilesListEntity> =  _data

    private val _isloading : MutableStateFlow<Boolean> = MutableStateFlow(true)
    var isloading: StateFlow<Boolean> =  _isloading

    private val _error : MutableStateFlow<Exception> = MutableStateFlow(Exception())
    var error: StateFlow<Exception> =  _error

    fun getFilesList() = viewModelScope.launch {
        mainUseCase.getFilesList().onEach {
            when(it){
                is DataState.Success ->{
                    _isloading.value = false
                    _data.value = it.data
                }
                is DataState.Error ->{
                    _isloading.value = false
                    _error.value = it.exception
                }
                is DataState.Loading ->{
                    _isloading.value = true
                }
            }
        }.launchIn(viewModelScope)

    }


    suspend fun downloadFile(file: File, url: Url):Flow<DownloadResult> = mainUseCase.downloadFile(file,url)

}