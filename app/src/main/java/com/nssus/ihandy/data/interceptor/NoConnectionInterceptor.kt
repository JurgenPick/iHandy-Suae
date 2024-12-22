package com.nssus.ihandy.data.interceptor

import android.content.Context
import com.nssus.ihandy.data.network.exception.NoConnectivityException
import com.nssus.ihandy.data.util.NetworkUtil.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response

class NoConnectionInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isNetworkAvailable(context)) {
            throw NoConnectivityException()
        } else {
            chain.proceed(chain.request())
        }
    }
}