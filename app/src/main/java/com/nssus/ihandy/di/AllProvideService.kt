package com.nssus.ihandy.di

import com.nssus.ihandy.BuildConfig
import com.nssus.ihandy.data.network.provideRetrofit
import com.nssus.ihandy.data.service.AuthenService
import com.nssus.ihandy.data.service.shipping.PcShippingService
import com.nssus.ihandy.data.service.common.shipbyproduct.ShipByProductService
import com.nssus.ihandy.data.service.common.yardentry.YardEntryService
import okhttp3.OkHttpClient
import retrofit2.create

// comment this if not use
//fun provideUserService(okHttpClient: OkHttpClient): UserService =
//    provideRetrofit(
//        baseUrl = BuildConfig.BASE_USER,
//        okHttpClient = okHttpClient
//    ).create(UserService::class.java)

// un comment this
// provide service for authentication service
fun provideAuthenService(okHttpClient: OkHttpClient): AuthenService =
    provideRetrofit(
        baseUrl = BuildConfig.BASE_AUTHEN,
        okHttpClient = okHttpClient
    ).create(AuthenService::class.java)

fun provideShipByProductService(okHttpClient: OkHttpClient): ShipByProductService =
    provideRetrofit(
        baseUrl = BuildConfig.BASE_COMMON,
        okHttpClient = okHttpClient
    ).create(ShipByProductService::class.java)

fun providePcShippingService(okHttpClient: OkHttpClient): PcShippingService =
    provideRetrofit(
        baseUrl = BuildConfig.BASE_SHIPPING,
        okHttpClient = okHttpClient
    ).create(PcShippingService::class.java)

fun provideYardEntry(okHttpClient: OkHttpClient) : YardEntryService =
    provideRetrofit(
        baseUrl = BuildConfig.BASE_COMMON,
        okHttpClient = okHttpClient
    ).create(YardEntryService::class.java)
