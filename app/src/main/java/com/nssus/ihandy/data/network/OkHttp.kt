package com.nssus.ihandy.data.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nssus.ihandy.BuildConfig
import com.nssus.ihandy.data.constant.ValueConstant
import com.nssus.ihandy.data.interceptor.NoConnectionInterceptor
import com.nssus.ihandy.data.interceptor.RequestInterceptor
import com.nssus.ihandy.data.interceptor.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }
}

fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    chucker: ChuckerInterceptor,
    noConnectionInterceptor: NoConnectionInterceptor,
    requestInterceptor: RequestInterceptor,
    tokenInterceptor: TokenInterceptor
): OkHttpClient {
    return OkHttpClient().newBuilder().apply {
        addNetworkInterceptor(loggingInterceptor)
        addInterceptor(chucker)
        addInterceptor(noConnectionInterceptor)
        addInterceptor(requestInterceptor)
        addInterceptor(tokenInterceptor)
        connectTimeout(ValueConstant.TIME_OUT, TimeUnit.SECONDS)
        readTimeout(ValueConstant.TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(ValueConstant.TIME_OUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
    }.build()
}

fun provideOkHttpClientForInterceptor(
    loggingInterceptor: HttpLoggingInterceptor,
    chucker: ChuckerInterceptor,
    noConnectionInterceptor: NoConnectionInterceptor
): OkHttpClient {
    return OkHttpClient().newBuilder().apply {
        addNetworkInterceptor(loggingInterceptor)
        addNetworkInterceptor(chucker)
        addInterceptor(noConnectionInterceptor)
        connectTimeout(ValueConstant.TIME_OUT, TimeUnit.SECONDS)
        readTimeout(ValueConstant.TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(ValueConstant.TIME_OUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
    }.build()
}

fun provideRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    factory: Converter.Factory = GsonConverterFactory.create()
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(factory)
        .client(okHttpClient)
        .build()
}