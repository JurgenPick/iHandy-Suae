package com.nssus.ihandy.data.usecase.common.yardentry

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.repository.common.yardentry.YardEntryRepository
import com.nssus.ihandy.model.common.yardentry.CoilResponse
import com.nssus.ihandy.model.common.yardentry.YardRequest
import com.nssus.ihandy.model.common.yardentry.YardResponse
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

class YardEntryUseCase(
    private val dispatcher: Dispatcher,
    private val yardEntryRepository: YardEntryRepository
) {
    fun getCheckCoil(coilNo :String) : Flow<NetworkResult<CoilResponse>> = flow {
        emit(NetworkResult.Loading)

        yardEntryRepository.checkCoilNo(coilNo).collect{
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())

    fun getCheckCoilWithSupplier(coilNo : String, supplier : String) : Flow<NetworkResult<CoilResponse>> = flow {
        emit(NetworkResult.Loading)

        yardEntryRepository.checkCoilNoWithSupplier(coilNo,supplier).collect{
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())

    fun getCheckYard(request: YardRequest) : Flow<NetworkResult<YardResponse>> = flow {
        emit(NetworkResult.Loading)

        yardEntryRepository.checkYard(request).collect{
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())

    fun sendMQ(request: YardRequest) : Flow<NetworkResult<ResponseBody>> = flow {
        emit(NetworkResult.Loading)

        yardEntryRepository.sendMQYardEntry(request).collect{
            emit(it)
        }
    }.flowOn(dispatcher.providesDefaultDispatcher())
}