package com.nssus.ihandy.model.production.crfhrec

import androidx.annotation.StringRes
import com.nssus.ihandy.R


data class CRFHReceiveUIStateModel(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val isClearAllTextFieldValue: Boolean = false,
    val isAlreadyInitialized: Boolean = false,
    var isGetCoilNumberResp: Boolean = false,
    var isGetYyrrccssResp: Boolean = false,
    var isGetSupplierNumberResp: Boolean = false,
    val message: String? = null,
    var successMsg: String? = null,
    val navigateType: CRFHReceiveNavigateType = CRFHReceiveNavigateType.NONE,

    var errorBody: CRFHReceiveErrorModel? = null,
    val coilNo: String = "",
    val yyrrccss: String = "",
    val supplierNo: String = "",
    val status: String = "",



)
enum class CRFHReceiveNavigateType {
    GO_BACK,
    DISPLAY_DIALOG_SUCCESS,
    NONE
}

enum class  CRFHReceiveErrorType(@StringRes val errorMsgId : Int = R.string.empty_string){
    ERROR_FROM_API,
    EMPTY_SHIPMENT_LOT(R.string.common_empty_fill_shipment_lot_warning_msg),
    DIGIT_SHIPMENT_LOT(R.string.ship_by_product_digit_shipment_lot_warning_msg),
    EMPTY_COIL_NUMBER(R.string.common_empty_fill_coil_number_warning_msg),
    EMPTY_COIL_LIST(R.string.ship_by_pro_list_coil_empty),
    ERROR_FROM_API_SHIPMENT_LOT,
    ERROR_FROM_API_COIL_NO,
    DUPLICATE_COIL_NUMBER(R.string.common_duplicate_coil_number_warning_msg),
    ERROR_FROM_API_SEND_MQ
}


data class CRFHReceiveErrorModel(
    val crfhReceiveErrorType: CRFHReceiveErrorType = CRFHReceiveErrorType.ERROR_FROM_API,
    val errorMsg : String? = null
)