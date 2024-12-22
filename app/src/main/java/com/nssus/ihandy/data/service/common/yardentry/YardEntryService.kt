package com.nssus.ihandy.data.service.common.yardentry

import com.nssus.ihandy.model.common.yardentry.CoilResponse
import com.nssus.ihandy.model.common.yardentry.YardRequest
import com.nssus.ihandy.model.common.yardentry.YardResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface YardEntryService {
    @GET("yard-entry/coil")
    suspend fun getCheckCoilNo(@Query("coil") coilNo : String,
                               @Query("deviceId") device : String): Response<CoilResponse>

    @GET("yard-entry/coil")
    suspend fun getCheckCoilNoWithSupplier(@Query("coil") coilNo : String,
                                           @Query("supplier") supplierNo : String,
                                           @Query("deviceId") device : String): Response<CoilResponse>

    @POST("yard-entry/yard")
    suspend fun getCheckYard(@Body checkYardRequest: YardRequest) : Response<YardResponse>

    @POST("yard-entry/submit")
    suspend fun sendMQYardEntry(@Body request: YardRequest) : Response<ResponseBody>

}