package com.example.nagwatask.data.datasource.RemoteDatasource

import io.reactivex.Observable

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {
    @Streaming
    @GET
    fun downloadFile(@Url url: String,): Observable<Response<ResponseBody>>
}