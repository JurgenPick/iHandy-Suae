package com.nssus.ihandy.model.shipping.pcshipping

import com.google.gson.annotations.SerializedName

data class GetCoilListResponse (
    @SerializedName("coilNo") val coilNumber : String? = null,
    @SerializedName("truckNo") val truckNumber : String? =null,
    @SerializedName("status") val shipStatus : String? =null,
    @SerializedName("dock") val dock : String? = null,
    @SerializedName("width") val width : Double? = 0.0
)

