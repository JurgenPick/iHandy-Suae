package com.nssus.ihandy.data.service.common.shipbyproduct


import com.nssus.ihandy.model.common.shipbypro.GetCheckCoilResponse
import com.nssus.ihandy.model.common.shipbypro.SendMqCoilListRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ShipByProductService {
    @GET("shipbypro/checkship")
    suspend fun getCheckShipmentLot(@Query("shiplot") shipmentLot : String): Response<ResponseBody>

    @GET("shipbypro/checkcoil")
    suspend fun getCheckCoil(@Query("shiplot") shipmentLot : String,
                             @Query("coilNo") coilNo : String): Response<GetCheckCoilResponse>

    @POST("shipbypro/sendmq")
    suspend fun sendMQ(@Body request: SendMqCoilListRequest) : Response<ResponseBody>
}