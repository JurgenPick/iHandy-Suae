package com.nssus.ihandy.di

import com.nssus.ihandy.data.interceptor.Chucker
import com.nssus.ihandy.data.interceptor.NoConnectionInterceptor
import com.nssus.ihandy.data.interceptor.RequestInterceptor
import com.nssus.ihandy.data.interceptor.TokenInterceptor
import com.nssus.ihandy.data.network.provideLoggingInterceptor
import com.nssus.ihandy.data.network.provideOkHttpClient
import com.nssus.ihandy.data.network.provideRetrofit
import com.nssus.ihandy.data.repository.AuthenRepository
import com.nssus.ihandy.data.repository.AuthenRepositoryImpl
import com.nssus.ihandy.data.repository.shipping.PcShippingRepository
import com.nssus.ihandy.data.repository.shipping.PcShippingRepositoryImpl
import com.nssus.ihandy.data.repository.common.shipbyproduct.ShipByProductRepository
import com.nssus.ihandy.data.repository.common.shipbyproduct.ShipByProductRepositoryImpl
import com.nssus.ihandy.data.repository.common.yardentry.YardEntryRepository
import com.nssus.ihandy.data.repository.common.yardentry.YardEntryRepositoryImpl
import com.nssus.ihandy.data.usecase.AuthenUseCase
import com.nssus.ihandy.data.usecase.shipping.PcShippingUseCase
import com.nssus.ihandy.data.usecase.common.shipbyproduct.ShipByProductUseCase
import com.nssus.ihandy.data.usecase.common.yardentry.YardEntryUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    // Interceptor
    single(named("chucker")) { Chucker.createChucker() }
    single { NoConnectionInterceptor(get()) }
    single { RequestInterceptor() } // get()
    single { provideLoggingInterceptor() }
    single { TokenInterceptor(get(),get(),get(named("chucker")),get()) }
//    single { AuthInterceptor(get(), get()) }

    // Network
    single { provideRetrofit(get(), get(), get()) }
    single { provideOkHttpClient(get(), get(named("chucker")), get(), get(),get()) } // get()

    // Service & Repository
    //single { provideUserService(get()) } // comment this if not use
    //single<UserRepository> { UserRepositoryImpl(get(), get()) } // comment this if not use
    single { provideAuthenService(get()) } // uncomment this
    single { provideShipByProductService(get()) }
    single { providePcShippingService(get()) }
    single { provideYardEntry(get()) }

    single<AuthenRepository> { AuthenRepositoryImpl(get(), get()) } // uncomment this
    single<ShipByProductRepository> { ShipByProductRepositoryImpl(get(), get()) }
    single<PcShippingRepository> { PcShippingRepositoryImpl(get(),get()) }
    single<YardEntryRepository> { YardEntryRepositoryImpl(get(),get()) }

    // UseCase
    single { AuthenUseCase(get(), get()) } // uncomment this
    single { ShipByProductUseCase(get(), get()) }
    single { PcShippingUseCase(get(), get()) }
    single { YardEntryUseCase(get(),get()) }
    //single { HomeUseCase(get(), get()) } // comment this
}