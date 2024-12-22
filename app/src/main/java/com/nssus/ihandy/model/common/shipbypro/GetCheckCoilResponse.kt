package com.nssus.ihandy.model.common.shipbypro

import com.google.gson.annotations.SerializedName

data class GetCheckCoilResponse (
    @SerializedName("shipmentLotNo") val shipmentLotNo: String? = null,
    @SerializedName("coilNo") val coilNo: String? = null,
    @SerializedName("shipStatus") val shipStatus: String? = null,
    @SerializedName("weight") val coilWeight: Double? = null,

    var isSelectedRemove: Boolean = false,

)