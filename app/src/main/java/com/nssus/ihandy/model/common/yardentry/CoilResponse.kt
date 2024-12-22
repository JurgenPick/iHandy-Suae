package com.nssus.ihandy.model.common.yardentry

import com.google.gson.annotations.SerializedName

data class CoilResponse (
    @SerializedName("coilNo") val coilNo :String? = null,
    @SerializedName("yard") val yard: YardInformation ?= null
)