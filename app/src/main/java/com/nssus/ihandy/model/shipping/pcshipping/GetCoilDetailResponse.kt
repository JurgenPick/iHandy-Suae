package com.nssus.ihandy.model.shipping.pcshipping

import com.google.gson.annotations.SerializedName

data class GetCoilDetailResponse(
    @SerializedName("coilNo") val coilNo : String? = null,
    @SerializedName("status") val statusCoil : String? = null,
    @SerializedName("needWidth") val needWidth : String?= null,
)