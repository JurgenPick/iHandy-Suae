package com.nssus.ihandy.model.common.yardentry

import com.google.gson.annotations.SerializedName

data class YardInformation(
    @SerializedName("code") val yardCode: String? = null,
    @SerializedName("row") val yardRow: String? = null,
    @SerializedName("column") val yardColumn: String? = null,
    @SerializedName("tier") val yardTier: String? = null
)