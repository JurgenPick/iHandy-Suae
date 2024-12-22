package com.nssus.ihandy.data.service

import com.nssus.ihandy.data.constant.AppConstant
import com.nssus.ihandy.data.constant.AppConstant.REFRESH_TOKEN
import com.nssus.ihandy.data.constant.ValueConstant
import com.nssus.ihandy.data.constant.ValueConstant.PREFIX_TOKEN
import com.nssus.ihandy.model.authen.GetUserInfoRequest
import com.nssus.ihandy.model.authen.GetUserInfoResponse
import com.nssus.ihandy.model.authen.LoginRequest
import com.nssus.ihandy.model.authen.LoginResponse
import com.nssus.ihandy.model.authen.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

   @GET("refreshToken") //
   suspend fun refreshToken(
       @Header("Authorization") authPrefixToken: String = "$PREFIX_TOKEN $REFRESH_TOKEN",
       @Header("refreshToken") refreshToken: String = REFRESH_TOKEN
   ): Response<RefreshTokenResponse>

    @GET("logout")
    suspend fun logout(
        @Header("authPrefixtoken") authPrefixToken: String,
        @Header("accessToken") accessToken: String,
        @Header("deviceId") deviceId: String
    ) : Response<Unit>
    //suspend fun logout(): Response<LogoutResponse> // อาจไม่ต้องมีเรสปอนโมเดล


    @GET("user")
    suspend fun getUserInfo(
        @Header("authPrefixtoken") authPrefixToken: String,
        @Header("accessToken") accessToken: String,
        @Header("deviceId") deviceId: String
    ): Response<GetUserInfoResponse>
}


