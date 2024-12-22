package com.nssus.ihandy.data.service.shipping

import com.nssus.ihandy.model.shipping.pcshipping.GetCoilDetailResponse
import com.nssus.ihandy.model.shipping.pcshipping.GetCoilListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PcShippingService {
    @GET("pcship/checkship")
    suspend fun getCheckShipmentLot(@Query("shiplot") shipmentLot : String) : Response<List<GetCoilListResponse>>

    @GET("pcship/checkcoil")
    suspend fun getCheckCoilNo(@Query("shiplot") shipmentLot: String,
                               @Query("coilNo") coilNumber: String):Response<GetCoilDetailResponse>
}