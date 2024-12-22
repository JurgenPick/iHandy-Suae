package com.nssus.ihandy.model.common.shipbypro

import com.google.gson.annotations.SerializedName

data class SendMqCoilListRequest(
    @SerializedName("shiplot") val shipmentLotNo: String? = null,
    @SerializedName("listCoil") val listCoilNo : List<String> = emptyList()
)