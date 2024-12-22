package com.nssus.ihandy.data.extension

import com.nssus.ihandy.model.common.shipbypro.GetCheckCoilResponse
import com.nssus.ihandy.model.shipping.pcshipping.CoilDetail

fun List<CoilDetail>.setMatchedItemFrom(searchCoilNo: String) {
    apply {
//        getMatchedItem()?.let { it.isMatched = false }
        find { searchCoilNo == it.getCoilListResponse.coilNumber }?.let { it.isMatched = true }
    }
}

//fun List<CoilDetail>.getMatchedCoilNumber(searchCoilNo: String) {
//    apply {
////        getMatchedItem()?.let { it.isMatched = false }
//        find { searchCoilNo == it.getCoilListResponse.coilNumber }?.let {
//            find { it.isMatched }
//        }
//    }
//}

//fun List<CoilDetail>.getMatchedItemWithCoilNumber(coilNo: String): CoilDetail? =
//    find { it.getCoilListResponse.coilNumber == coilNo}

fun List<CoilDetail>.getMatchedItemWithCoilNumber(coilNo: String): CoilDetail? =
    find { it.getCoilListResponse.coilNumber == coilNo }

//fun List<CoilDetail>.getMatchedItem(): CoilDetail? = find { it.isMatched }

//fun List<CoilDetailItem>.getMatchedItemCoilNo(): String? = getSelectedItem()?.coilNo // ถ้าเป็นเรสปอนเอพีไอ ค่อย dot เข้าไปอีกชั้นเพื่อเอาค่า coil เป็นต้น

fun CoilDetail?.isNull(): Boolean = this == null

fun CoilDetail?.isNotNull(): Boolean = isNull().not()

//Coil list Ship by pro
fun ArrayList<GetCheckCoilResponse>.setSelectedRemoveFrom(selectedCoil: GetCheckCoilResponse): ArrayList<GetCheckCoilResponse> =
    apply {
        find { it.coilNo == selectedCoil.coilNo }
            ?.let { it.isSelectedRemove = it.isSelectedRemove.not() }

    }
