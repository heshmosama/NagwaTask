package com.example.nagwatask.data.datasource.RemoteDatasource

import com.example.nagwatask.core.utils.DownloadResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt

suspend fun HttpClient.downloadFile(file: File, url: Url): Flow<DownloadResult> {
    return flow {


        try {
            for (i in 0..3){

                var response = call {
                    url(url)
                    method = HttpMethod.Get
                }.response



                if (response.contentLength() != null){
                    val data = ByteArray(response.contentLength()!!.toInt())
                    var offset = 0
                    do {
                        val currentRead = response.content.readAvailable(data, offset, data.size)
                        offset += currentRead
                        val progress = (offset * 100f / data.size).roundToInt()
                        emit(DownloadResult.Progress(progress))
                    } while (currentRead > 0)
                    response.close()
                    if (response.status.isSuccess()) {
                        val outputfile = FileOutputStream(file)
                        outputfile.write(data)
                        outputfile.close()
                        emit(DownloadResult.Success)
                    } else {
                        emit(DownloadResult.Error("File not downloaded"))
                    }
                    break
                }else{
                    if (i == 3 ) emit(DownloadResult.Error("File not downloaded"))
                }

            }
        }catch (e:Exception){
            emit(DownloadResult.Error("File not downloaded"))
        }
    }
}