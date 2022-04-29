package com.yoenas.githubusers.core.di

import com.yoenas.githubusers.core.BuildConfig
import com.yoenas.githubusers.core.data.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/uyPYgclc5Jt69vKu92vci6etcBDY8UNTyrHQZJpVoZY=")
            .add(hostname, "sha256/e0IRz5Tio3GA1Xs4fUVWmH1xHDiH2dMbVtCBSkOIdqM=")
            .add(hostname, "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=")
            .build()

        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val defaultHTTPClient = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", BuildConfig.API_KEY)
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(defaultHTTPClient)
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun providesApiService(okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}