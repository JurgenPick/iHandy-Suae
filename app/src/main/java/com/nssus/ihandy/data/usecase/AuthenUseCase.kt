package com.nssus.ihandy.data.usecase

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.repository.AuthenRepository
import com.nssus.ihandy.model.authen.GetUserInfoRequest
import com.nssus.ihandy.model.authen.GetUserInfoResponse
import com.nssus.ihandy.model.authen.LoginRequest
import com.nssus.ihandy.model.authen.LoginResponse
import com.nssus.ihandy.model.home.UserInfoResponse
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthenUseCase( // ใช้หน้าล้อคอิน(login) ใช้หน้าเมน(logout getuserinfo)
    private val dispatcher: Dispatcher,
    private val authenRepo: AuthenRepository
) {
    fun login(request: LoginRequest): Flow<NetworkResult<LoginResponse>> = flow {
        emit(NetworkResult.Loading)

        authenRepo.login(request).collect {
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())

        fun logout(authPrefixtoken: String,
                   accessToken: String,
                   deviceId: String): Flow<NetworkResult<Unit>> = flow { // อาจไม่ต้องมีเรสปอนโมเดล
        emit(NetworkResult.Loading)

        authenRepo.logout(authPrefixtoken, accessToken, deviceId).collect {
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())

//    fun logout(): Flow<NetworkResult<LogoutResponse>> = flow { // อาจไม่ต้องมีเรสปอนโมเดล
//        emit(NetworkResult.Loading)
//
//        authenRepo.logout().collect {
//            emit(it)
//        }
//    }.flowOn(dispatcher.providesIODispatcher())
//
    fun getUserInfo(
        authPrefixtoken: String,
        accessToken: String,
        deviceId: String
    ): Flow<NetworkResult<GetUserInfoResponse>> = flow {
        emit(NetworkResult.Loading)

        authenRepo.getUserInfo(authPrefixtoken, accessToken, deviceId).collect {
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())
}