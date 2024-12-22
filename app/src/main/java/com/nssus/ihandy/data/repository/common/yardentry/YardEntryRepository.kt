package com.nssus.ihandy.data.repository.common.yardentry

import android.os.Build
import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.network.baseNetworkFilter
import com.nssus.ihandy.data.service.common.yardentry.YardEntryService
import com.nssus.ihandy.model.common.yardentry.CoilResponse
import com.nssus.ihandy.model.common.yardentry.YardRequest
import com.nssus.ihandy.model.common.yardentry.YardResponse
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

interface YardEntryRepository{
    fun checkCoilNo(coilNo:String) : Flow<NetworkResult<CoilResponse>>
    fun checkCoilNoWithSupplier(coilNo: String,supplierNo : String) : Flow<NetworkResult<CoilResponse>>
    fun checkYard(request: YardRequest) : Flow<NetworkResult<YardResponse>>
    fun sendMQYardEntry(request: YardRequest) : Flow<NetworkResult<ResponseBody>>
}

class YardEntryRepositoryImpl(
    private val dispatcher: Dispatcher,
    private val yardEntrySv : YardEntryService
):YardEntryRepository{
    override fun checkCoilNo(coilNo: String): Flow<NetworkResult<CoilResponse>> = flow {
        emit(baseNetworkFilter { yardEntrySv.getCheckCoilNo(coilNo, Build.DEVICE) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

    override fun checkCoilNoWithSupplier(
        coilNo: String,
        supplierNo: String
    ): Flow<NetworkResult<CoilResponse>> = flow {
        emit(baseNetworkFilter { yardEntrySv.getCheckCoilNoWithSupplier(coilNo, supplierNo, Build.DEVICE) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

    override fun checkYard(request: YardRequest): Flow<NetworkResult<YardResponse>> = flow {
        emit(baseNetworkFilter { yardEntrySv.getCheckYard(request) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

    override fun sendMQYardEntry(request: YardRequest): Flow<NetworkResult<ResponseBody>> = flow {
        emit(baseNetworkFilter { yardEntrySv.sendMQYardEntry(request) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

}