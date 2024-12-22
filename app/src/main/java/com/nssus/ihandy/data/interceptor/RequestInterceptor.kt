package com.nssus.ihandy.data.interceptor

import android.content.SharedPreferences
import android.os.Build
import com.nssus.ihandy.BuildConfig
import com.nssus.ihandy.data.constant.AppConstant.APP_TOKEN
import com.nssus.ihandy.data.constant.ValueConstant.REQ_HEADER_AUTHORIZATION
import com.nssus.ihandy.data.constant.ValueConstant.REQ_HEADER_KEY_CONTENT_TYPE
import com.nssus.ihandy.data.constant.ValueConstant.REQ_HEADER_USER_AGENT
import com.nssus.ihandy.data.constant.ValueConstant.REQ_HEADER_VALUE_CONTENT_TYPE
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor() : Interceptor { // private val sharedPreferences: SharedPreferences
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().apply {
            addHeader(REQ_HEADER_KEY_CONTENT_TYPE, REQ_HEADER_VALUE_CONTENT_TYPE)

//            val lang =
//                sharedPreferences.getString(KEY_LANG, null) ?: LocaleUtils.getDefaultLanguage()
//            addHeader(REQ_HEADER_LANGUAGE, lang.uppercase())

            addHeader(REQ_HEADER_AUTHORIZATION, "Bearer ${APP_TOKEN}")
            addHeader(
                REQ_HEADER_USER_AGENT,
                "ihandy-android/version: ${BuildConfig.VERSION_NAME}; " +
                        "application id: ${BuildConfig.APPLICATION_ID}; " +
                        "version code: ${BuildConfig.VERSION_CODE}; " +
                        "android version: ${Build.VERSION.RELEASE}; " +
                        "model: ${Build.MODEL}; " +
                        "device: ${Build.DEVICE}; " +
                        "brand: ${Build.BRAND};"
            )
        }

        return chain.proceed(newRequest.build())
    }
}