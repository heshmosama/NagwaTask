package com.ips.sdxapp.core.di

import com.example.nagwatask.data.datasource.RemoteDatasource.NetworkService
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.ktor.client.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.*
import kotlin.jvm.Throws

@Module
@InstallIn(ApplicationComponent::class)
object KtorServiceModule {
    @Singleton
    @Provides
    fun getMainRepo() = HttpClient()
}