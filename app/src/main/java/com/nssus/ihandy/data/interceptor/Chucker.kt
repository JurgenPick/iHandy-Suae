package com.nssus.ihandy.data.interceptor

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.nssus.ihandy.MainApplication

object Chucker {
    fun createChucker(): ChuckerInterceptor {
        val chuckerCollector =
            ChuckerCollector(MainApplication.getContext(), true, RetentionManager.Period.ONE_DAY)
        return ChuckerInterceptor
            .Builder(MainApplication.getContext())
            .collector(chuckerCollector)
            .maxContentLength(250000L)
            .alwaysReadResponseBody(true)
            .build()
    }
}