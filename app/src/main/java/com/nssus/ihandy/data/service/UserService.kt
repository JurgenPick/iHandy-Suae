package com.nssus.ihandy.data.service

import com.nssus.ihandy.model.home.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("publish")
    suspend fun getUserInfo(): Response<List<UserInfoResponse>>
}