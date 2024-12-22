package com.nssus.ihandy.data.repository

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.network.baseNetworkFilter
import com.nssus.ihandy.data.service.AuthenService
import com.nssus.ihandy.model.authen.GetUserInfoRequest
import com.nssus.ihandy.model.authen.GetUserInfoResponse
import com.nssus.ihandy.model.authen.LoginRequest
import com.nssus.ihandy.model.authen.LoginResponse
import com.nssus.ihandy.model.home.UserInfoResponse
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface AuthenRepository {
    fun login(req: LoginRequest): Flow<NetworkResult<LoginResponse>>
    fun logout(authPrefixtoken: String,
               accessToken: String,
               deviceId: String): Flow<NetworkResult<Unit>>
    fun getUserInfo(authPrefixtoken: String,
                    accessToken: String,
                    deviceId: String): Flow<NetworkResult<GetUserInfoResponse>>
}

class AuthenRepositoryImpl(
    private val dispatcher: Dispatcher,
    private val authenSv: AuthenService,
//    private val sharePrefs: SharedPreferences
) : AuthenRepository {
    override fun login(req: LoginRequest): Flow<NetworkResult<LoginResponse>> = flow {
        emit(baseNetworkFilter { authenSv.login(req) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

    override fun logout(authPrefixtoken: String,
                        accessToken: String,
                        deviceId: String): Flow<NetworkResult<Unit>> = flow {
        emit(baseNetworkFilter<Unit> { authenSv.logout(authPrefixtoken, accessToken, deviceId) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

    override fun getUserInfo(
        authPrefixtoken: String,
        accessToken: String,
        deviceId: String
    ): Flow<NetworkResult<GetUserInfoResponse>> = flow {
        emit(baseNetworkFilter { authenSv.getUserInfo(authPrefixtoken, accessToken, deviceId) })
    }.flowOn(dispatcher.providesDefaultDispatcher())
}