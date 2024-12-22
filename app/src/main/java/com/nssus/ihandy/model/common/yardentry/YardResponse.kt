package com.nssus.ihandy.model.common.yardentry

import com.google.gson.annotations.SerializedName

data class YardResponse(
    @SerializedName("reservedCoilNo") val reservedYard :String? = null,
    @SerializedName("yard") val yard: YardInformation ?= null
)