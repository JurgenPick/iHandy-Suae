package com.nssus.ihandy.data.repository

import android.content.SharedPreferences
import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.network.baseNetworkFilter
import com.nssus.ihandy.data.service.UserService
import com.nssus.ihandy.model.home.UserInfoResponse
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface UserRepository {
    fun getUserInfo(): Flow<NetworkResult<List<UserInfoResponse>>>
}

class UserRepositoryImpl(
    private val dispatcher: Dispatcher,
    private val userSv: UserService,
//    private val sharePrefs: SharedPreferences
) : UserRepository {
    override fun getUserInfo(): Flow<NetworkResult<List<UserInfoResponse>>> = flow {
        emit(baseNetworkFilter { userSv.getUserInfo() })
    }.flowOn(dispatcher.providesDefaultDispatcher())
}