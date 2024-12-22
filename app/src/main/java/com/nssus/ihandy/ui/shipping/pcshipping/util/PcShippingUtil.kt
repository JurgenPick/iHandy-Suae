package com.nssus.ihandy.ui.shipping.pcshipping.util

import com.nssus.ihandy.model.shipping.pcshipping.CoilDetail
import com.nssus.ihandy.model.shipping.pcshipping.GetCoilListResponse

object PcShippingUtil {
    fun List<GetCoilListResponse>?.mapToCoilDetail() : List<CoilDetail>{
        if (isNullOrEmpty()) return emptyList()
        val coilDetail = arrayListOf<CoilDetail>()

        forEach {
            coilDetail.add(
                CoilDetail(
                    getCoilListResponse = it
                )
            )
        }
        return coilDetail
    }
}