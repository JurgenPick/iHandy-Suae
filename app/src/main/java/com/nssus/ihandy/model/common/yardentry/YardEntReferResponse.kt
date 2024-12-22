package com.nssus.ihandy.model.common.yardentry

import com.google.gson.annotations.SerializedName
data class YardEntReferResponse(
    @SerializedName("coilNo") val coilNo: String? = null,
    @SerializedName("matClass") val matClass: String? = null,
    @SerializedName("matStatus") val matStatus: String? = null,
    @SerializedName("acceptRejectClass") val acceptRejectClass: String? = null,
    @SerializedName("nextLine") val nextLine: String? = null,
    @SerializedName("previousLine") val previousLine: String? = null,
    @SerializedName("relabelStatus") val relabelStatus: String? = null,
    @SerializedName("yard") val yard: String? = null,
    @SerializedName("code") val code: String? = null,
    @SerializedName("row") val row: String? = null,
    @SerializedName("column") val column: String? = null,
    @SerializedName("tier") val tier: String? = null
)