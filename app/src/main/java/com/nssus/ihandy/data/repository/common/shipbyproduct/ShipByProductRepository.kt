package com.nssus.ihandy.data.repository.common.shipbyproduct

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.network.baseNetworkFilter
import com.nssus.ihandy.data.service.common.shipbyproduct.ShipByProductService
import com.nssus.ihandy.model.common.shipbypro.GetCheckCoilResponse
import com.nssus.ihandy.model.common.shipbypro.SendMqCoilListRequest
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import okhttp3.ResponseBody

interface ShipByProductRepository{
    fun checkShipmentLot(shipmentLot : String): Flow<NetworkResult<ResponseBody>>
    fun checkCoilNo(shipmentLot: String,coilNo :String) : Flow<NetworkResult<GetCheckCoilResponse>>
    fun sendMQ(request: SendMqCoilListRequest) : Flow<NetworkResult<ResponseBody>>
}

class ShipByProductRepositoryImpl(
    private val dispatcher: Dispatcher,
    private val shipByProductSv : ShipByProductService
) : ShipByProductRepository {
    override fun checkShipmentLot(shipmentLot: String):Flow<NetworkResult<ResponseBody>> = flow {
        emit(baseNetworkFilter { shipByProductSv.getCheckShipmentLot(shipmentLot) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

    override fun checkCoilNo(shipmentLot: String,coilNo :String): Flow<NetworkResult<GetCheckCoilResponse>> = flow {
        emit(baseNetworkFilter { shipByProductSv.getCheckCoil(shipmentLot,coilNo) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

    override fun sendMQ(request: SendMqCoilListRequest): Flow<NetworkResult<ResponseBody>> = flow {
        emit(baseNetworkFilter { shipByProductSv.sendMQ(request) })
    }.flowOn(dispatcher.providesDefaultDispatcher())


}

