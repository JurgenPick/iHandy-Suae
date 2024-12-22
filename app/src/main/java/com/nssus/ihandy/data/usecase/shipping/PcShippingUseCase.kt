package com.nssus.ihandy.data.usecase.shipping

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.repository.shipping.PcShippingRepository
import com.nssus.ihandy.model.network.NetworkResult
import com.nssus.ihandy.model.shipping.pcshipping.GetCoilDetailResponse
import com.nssus.ihandy.model.shipping.pcshipping.GetCoilListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PcShippingUseCase(
    private val dispatcher: Dispatcher,
    private val pcShippingRepository: PcShippingRepository
) {
    fun getCheckShipmentLot(shipmentLot : String) : Flow<NetworkResult<List<GetCoilListResponse>>> = flow {
        emit(NetworkResult.Loading)

        pcShippingRepository.checkShipmentLot(shipmentLot).collect{
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())

    fun getCheckCoilNo(shipmentLot: String,coilNo : String) : Flow<NetworkResult<GetCoilDetailResponse>> = flow {
        emit(NetworkResult.Loading)

        pcShippingRepository.checkCoilNo(shipmentLot, coilNo).collect{
            emit(it)
        }
    }.flowOn(dispatcher.providesIODispatcher())
}