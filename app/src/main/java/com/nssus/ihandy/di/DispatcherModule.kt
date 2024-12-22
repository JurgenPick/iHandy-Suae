package com.nssus.ihandy.di

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.DispatcherImpl
import org.koin.dsl.module

val dispatcherModule = module {
    single<Dispatcher> { DispatcherImpl() }
}