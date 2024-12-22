package com.nssus.ihandy.data.usecase

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.repository.UserRepository
import com.nssus.ihandy.model.home.UserInfoResponse
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeUseCase(
    private val dispatcher: Dispatcher,
    private val userRepo: UserRepository
) {
    fun getUserInfo(): Flow<NetworkResult<List<UserInfoResponse>>> = flow {// NetworkResult<UserInfoResponse>
        emit(NetworkResult.Loading)

        userRepo.getUserInfo().collect {
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())
}