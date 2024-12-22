package com.nssus.ihandy.data.repository.shipping

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.network.baseNetworkFilter
import com.nssus.ihandy.data.service.shipping.PcShippingService
import com.nssus.ihandy.model.network.NetworkResult
import com.nssus.ihandy.model.shipping.pcshipping.GetCoilDetailResponse
import com.nssus.ihandy.model.shipping.pcshipping.GetCoilListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface PcShippingRepository{
    fun checkShipmentLot(shipmentLot :String) : Flow<NetworkResult<List<GetCoilListResponse>>>
    fun checkCoilNo(shipmentLot : String, coilNo : String) :Flow<NetworkResult<GetCoilDetailResponse>>
}
class PcShippingRepositoryImpl(
    private val dispatcher: Dispatcher,
    private val pcShippingSv: PcShippingService
) : PcShippingRepository {

    override fun checkShipmentLot(shipmentLot: String): Flow<NetworkResult<List<GetCoilListResponse>>> = flow {
        emit(baseNetworkFilter { pcShippingSv.getCheckShipmentLot(shipmentLot) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

    override fun checkCoilNo(
        shipmentLot: String,
        coilNo: String
    ): Flow<NetworkResult<GetCoilDetailResponse>> = flow {
        emit(baseNetworkFilter { pcShippingSv.getCheckCoilNo(shipmentLot,coilNo) })
    }.flowOn(dispatcher.providesDefaultDispatcher())

}