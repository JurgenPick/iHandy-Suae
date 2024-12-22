package com.nssus.ihandy.model.common.yardentry

import com.google.gson.annotations.SerializedName
data class YardEntReferRequest(
    @SerializedName("coil") val coil: String? = null,
    @SerializedName("supplier") val supplier: String? = null,
    @SerializedName("deviceId") val deviceId: String? = null
)
