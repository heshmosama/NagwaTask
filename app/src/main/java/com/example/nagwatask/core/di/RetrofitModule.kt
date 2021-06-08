package com.ips.sdxapp.core.di

import com.example.nagwatask.data.datasource.RemoteDatasource.NetworkService
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGSONBuilder() :Gson{
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

//    private fun provideCacheInterceptor(context: Context): Interceptor {
//        return Interceptor { chain ->
//            val request = chain.request()
//            var cacheHeaderValue = if (!hasNetwork(context)!!){
//                "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 1
//            } else {
//                "public, max-age=" + 5
//            }Z
//            //request = request.newBuilder().build()
//            val response = chain.proceed(request)
//            response.newBuilder()
//                .removeHeader("Pragma")
//                .removeHeader("Cache-Control")
//                .header("Cache-Control", cacheHeaderValue)
//                .build()
//        }
//    }
    @Singleton
    @Provides
    fun provideSSLSocketFactory(trustManagerArray:Array<TrustManager>): SSLSocketFactory {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null,trustManagerArray, SecureRandom())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return sslContext!!.socketFactory
    }
    @Singleton
    @Provides
    fun provideTrustManager(): Array<TrustManager> {
        return arrayOf(
            object : X509TrustManager {
                @Suppress("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Suppress("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
    }

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(sslSocketFactory: SSLSocketFactory,trustManagerArray:Array<TrustManager>,httpLoggingInterceptor:HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(
            sslSocketFactory,
            (trustManagerArray[0] as X509TrustManager?)!!
        )
        builder.hostnameVerifier(HostnameVerifier {hostName: String?, session: SSLSession? -> true })
        builder.connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
        builder.addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Content_Type", "application/json").build()
            chain.proceed(newRequest)
        }
        builder.addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson,okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://developer.android.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): NetworkService {
        return retrofit
            .build()
            .create(NetworkService::class.java)
    }

}