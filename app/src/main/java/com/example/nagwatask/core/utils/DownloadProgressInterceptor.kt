package com.example.nagwatask.core.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class DownloadProgressInterceptor(private val listener: DownloadProgressListener) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .body(DownloadProgressResponseBody(originalResponse.body(), listener))
            .build()
    }
}