package com.example.nagwatask.core.utils

import android.util.Log
import com.example.nagwatask.data.datasource.RemoteDatasource.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit


class DownloadAPI(url: String?, listener: DownloadProgressListener?) {
    var retrofit: Retrofit

    fun downloadAPK(url: String, file: File?, subscriber: (InputStream) -> Any) {
        Log.d(TAG, "downloadAPK: $url")

        retrofit.create(NetworkService::class.java)
            .downloadFile(url)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .map(Function {
                it.body()?.byteStream()
            })
            .observeOn(Schedulers.computation())
            .doOnNext(Consumer {
               val stream = it.also {

               }
            }).doOnComplete {
                Log.v("done","hesham j")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                throw it
            }
            .subscribe(
                Consumer {
                    it?.let {
                        subscriber(it)
                    }
                }
            )
    }



    companion object {
        private const val TAG = "DownloadAPI"
        private const val DEFAULT_TIMEOUT = 15
    }

    init {
        val interceptor = DownloadProgressInterceptor(listener!!)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.learningcontainer.com/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}