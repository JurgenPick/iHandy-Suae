package com.nssus.ihandy.data.usecase.common.shipbyproduct

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.repository.common.shipbyproduct.ShipByProductRepository
import com.nssus.ihandy.model.common.shipbypro.GetCheckCoilResponse
import com.nssus.ihandy.model.common.shipbypro.SendMqCoilListRequest
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

class ShipByProductUseCase (
    private val dispatcher: Dispatcher,
    private val shipByProductRepository: ShipByProductRepository
){
    fun getCheckShipmentLot(shipmentLot : String) : Flow<NetworkResult<ResponseBody>> = flow {
        emit(NetworkResult.Loading)

        shipByProductRepository.checkShipmentLot(shipmentLot).collect {
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())

    fun getCheckCoilNumber(shipmentLot: String,coilNo :String) : Flow<NetworkResult<GetCheckCoilResponse>> = flow {
        emit(NetworkResult.Loading)

        shipByProductRepository.checkCoilNo(shipmentLot,coilNo).collect {
            emit(it)
        }

    }.flowOn(dispatcher.providesIODispatcher())

    fun sendMQ(request: SendMqCoilListRequest) : Flow<NetworkResult<ResponseBody>> = flow {
        emit(NetworkResult.Loading)

        shipByProductRepository.sendMQ(request).collect{
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())
}