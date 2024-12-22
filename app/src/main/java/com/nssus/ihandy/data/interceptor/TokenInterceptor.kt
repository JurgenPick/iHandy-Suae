package com.nssus.ihandy.data.interceptor

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nssus.ihandy.BuildConfig
import com.nssus.ihandy.data.constant.AppConstant.APP_TOKEN
import com.nssus.ihandy.data.constant.AppConstant.REFRESH_TOKEN
import com.nssus.ihandy.data.constant.ValueConstant.PREFIX_TOKEN
import com.nssus.ihandy.data.constant.ValueConstant.REQ_HEADER_AUTHORIZATION
import com.nssus.ihandy.data.extension.restartIHandyApp
import com.nssus.ihandy.data.network.exception.UnAuthorizeException
import com.nssus.ihandy.data.network.exception.UpStreamServerException
import com.nssus.ihandy.data.network.provideOkHttpClientForInterceptor
import com.nssus.ihandy.data.network.provideRetrofit
import com.nssus.ihandy.data.service.AuthenService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.net.HttpURLConnection

class TokenInterceptor(
    private val context: Context,
    private val loggingInterceptor: HttpLoggingInterceptor,
    private val chucker: ChuckerInterceptor,
    private val noConnectionInterceptor: NoConnectionInterceptor,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Attach the access token to the header if available
//        val authenticatedRequest = originalRequest.newBuilder()
//            .header("Authorization", "Bearer ${AppConstant.APP_TOKEN}")
//            .build()

        var response = chain.proceed(originalRequest)

        println(response.code)
        println(response.body)
        println(response.message)
        println(APP_TOKEN)
        println(REFRESH_TOKEN)
        when(response.code) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                response.close() // Close the response before making a new request
//        if (response.code == HttpURLConnection.HTTP_OK && !(originalRequest.url.toString().contains("/authen/login"))) {

                runBlocking {
                    println("Send refresh")
                    println(REFRESH_TOKEN)

                    // Create Auth Service and call refreshToken Api to get new access token
                    val refreshTokenResponse = createAuthService().refreshToken()

                    // Check refreshTokenResponse is successful
                    if (refreshTokenResponse.isSuccessful){
                        // Get refreshTokenResponse data
                        val refreshTokenData = refreshTokenResponse.body()
                        println("refreshTokenResponse data: $refreshTokenData")
                        val newAccessToken = refreshTokenData?.accessToken
                        val newRefreshToken = refreshTokenData?.refreshToken

                        // Store Necessary Token into Global Variable to use anywhere in application
                        APP_TOKEN = if (newAccessToken.isNullOrEmpty()) APP_TOKEN else newAccessToken
                        REFRESH_TOKEN = if (newRefreshToken.isNullOrEmpty()) REFRESH_TOKEN else newRefreshToken

                        // Set up the original request with the new token
                        val requestWithNewToken = originalRequest.newBuilder()
                            .header(REQ_HEADER_AUTHORIZATION, "$PREFIX_TOKEN $APP_TOKEN")
                            .build()

                        // Call The Same Api with New-Token Request
                        val responseWithNewToken = chain.proceed(requestWithNewToken)

                        // Return Response
                        return@runBlocking responseWithNewToken

                    }else{
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(
                                context,
                                "Your session has expired. Please log in again.",
                                Toast.LENGTH_LONG
                            ).show()
                            context.restartIHandyApp()
                        }
                    }
                }
            }
            HttpURLConnection.HTTP_BAD_GATEWAY ->{
                println("BAD_GATEWAY")
                throw UpStreamServerException()
            }
        }
        return response
    }

    private fun createAuthService():AuthenService{
        val okHttpClient = provideOkHttpClientForInterceptor(
            loggingInterceptor = loggingInterceptor,
            chucker = chucker,
            noConnectionInterceptor = noConnectionInterceptor
        )

        val retrofit = provideRetrofit(
            baseUrl = BuildConfig.BASE_AUTHEN,
            okHttpClient = okHttpClient
        )
        return retrofit.create(AuthenService::class.java)
    }

//    private fun refreshAccessToken(): String? {
//        return runBlocking {
//            // Call the refresh token API and handle the response
//            val result = authenService.refreshToken(
//                authPrefixToken = "${ValueConstant.PREFIX_TOKEN} ${AppConstant.REFRESH_TOKEN}"
//            )
//
//            // Check if the result is a successful response
//            if (result is NetworkResult.Success<*>) {
//                // Check if the data is of type RefreshTokenResponse
//                val responseData = result.data
//                if (responseData is RefreshTokenResponse) {
//                    val newAccessToken = responseData.accessToken
//                    val newRefreshToken = responseData.refreshToken
//
//                    // Update tokens in AppConstant if they are not null or empty
//                    if (!newAccessToken.isNullOrEmpty()) {
//                        AppConstant.APP_TOKEN = newAccessToken
//                    }
//                    if (!newRefreshToken.isNullOrEmpty()) {
//                        AppConstant.REFRESH_TOKEN = newRefreshToken
//                    }
//
//                    return@runBlocking newAccessToken
//                }
//            }
//            null // Token refresh failed
//        }
//    }

    // Check for 401 error to attempt token refresh
//    if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) { // code = 401
//        response.close() // Close the response before making a new request
////        if (response.code == HttpURLConnection.HTTP_OK && !(originalRequest.url.toString().contains("/authen/login"))) {
//
//        runBlocking {
//            println("Send refresh")
//            println(REFRESH_TOKEN)
//
//            // Create Auth Service and call refreshToken Api to get new access token
//            val refreshTokenResponse = createAuthService().refreshToken()
//
//            // Check refreshTokenResponse is successful
//            if (refreshTokenResponse.isSuccessful){
//                // Get refreshTokenResponse data
//                val refreshTokenData = refreshTokenResponse.body()
//                println("refreshTokenResponse data: $refreshTokenData")
//                val newAccessToken = refreshTokenData?.accessToken
//                val newRefreshToken = refreshTokenData?.refreshToken
//
//                // Store Necessary Token into Global Variable to use anywhere in application
//                APP_TOKEN = if (newAccessToken.isNullOrEmpty()) APP_TOKEN else newAccessToken
//                REFRESH_TOKEN = if (newRefreshToken.isNullOrEmpty()) REFRESH_TOKEN else newRefreshToken
//
//                println("New APP Token: $APP_TOKEN")
//                println("New Refresh Token: $REFRESH_TOKEN")
//
//                // Set up the original request with the new token
//                val requestWithNewToken = originalRequest.newBuilder()
//                    .header(REQ_HEADER_AUTHORIZATION, "$PREFIX_TOKEN $APP_TOKEN")
//                    .build()
//
//                // Call The Same Api with New-Token Request
//                val responseWithNewToken = chain.proceed(requestWithNewToken)
//
//                // Return Response
//                return@runBlocking responseWithNewToken
//
//            }else{
////                    println(response.code)
////                    println(response.body)
////                    println("Test throw")
////                    throw UnAuthorizeException()
//                Handler(Looper.getMainLooper()).post {
//                    Toast.makeText(
//                        context,
//                        "Your session has expired. Please log in again.",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    context.restartIHandyApp()
//                }
//            }
//        }
//    }


}
