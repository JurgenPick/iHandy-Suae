package com.nssus.ihandy.model.common.yardentry

import com.google.gson.annotations.SerializedName

data class YardRequest(
    @SerializedName("coilNo") val coilNo :String? = null,
    @SerializedName("supplierNo") val supplierNo :String? = null,
    @SerializedName("deviceId") val deviceId :String? = null,
    @SerializedName("yard") val yard: YardInformation ?= null
)