package com.nssus.ihandy

import android.app.Application
import android.content.Context
import com.nssus.ihandy.di.appModule
import com.nssus.ihandy.di.dispatcherModule
import com.nssus.ihandy.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class MainApplication : Application() {
    companion object {
        lateinit var mainApplication: MainApplication
        fun getContext(): Context = mainApplication.applicationContext
    }

    private val moduleList: List<Module> = listOf(
        dispatcherModule,
        appModule,
        networkModule
    )

    override fun onCreate() {
        super.onCreate()
        mainApplication = this

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(moduleList)
        }
    }

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base?.let { LocaleUtils.setLocale(it, null) })
//    }
}