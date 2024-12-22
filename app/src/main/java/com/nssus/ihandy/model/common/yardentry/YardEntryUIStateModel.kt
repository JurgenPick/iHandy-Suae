package com.nssus.ihandy.model.common.yardentry

import androidx.annotation.StringRes
import com.nssus.ihandy.R

data class YardEntryUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: YardEntryNavigateType = YardEntryNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: YardEntryErrorModel? = null,
    // Value
    var coilNo: String = "", //
    var yyrrcct: String = "", //
    var supplierNo: String = "", //
    var yardCurrent : String = "",

    // Flag
    var isGetCoilSuccess: Boolean = false, //
    var isGetCoilWithSupplierSuccess: Boolean = false,
    var isGetCoilFail : Boolean = false,
    var isGetSupplierNoSuccess: Boolean = false,
    var isGetSupplierNoFail: Boolean = false,
    var isGetYYRRCCTSuccess: Boolean = false,
    var isGetYYRRCCTFail: Boolean = false,
    var isClearAllTextFieldValue: Boolean = false,
    var isClickNextActionCoilNo: Boolean =false,
    var needSupplierNo : Boolean = false,
    var atErrorInYardTextBox : Boolean = false,
    var isConfirmYard : Boolean = false
)

enum class YardEntryNavigateType {
    GO_BACK,
    DISPLAY_CLEAR_ALL_DIALOG,
    RESERVED_COIL_NO,
    DISPLAY_SUCCESS,
//    DISPLAY_DOWNLOAD_SUCCESS_DIALOG,
//    BACK_TO_SELECT_OR_SEARCH_COMP,
    NONE
}

data class YardEntryErrorModel(
    val yardEntryErrorType: YardEntryErrorType = YardEntryErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class YardEntryErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    ERROR_EMPTY_COIL(R.string.yard_entry_error_empty_coil),
    ERROR_EMPTY_SUPPLIER(R.string.yard_entry_error_empty_supplier),
    ERROR_EMPTY_YARD(R.string.yard_entry_error_empty_yard),
    ERROR_FORMAT_YARD(R.string.yard_entry_error_format_yard),
    ERROR_HC_TO_CDCM(R.string.yard_entry_error_hc_discharge),
    ERROR_SEND_MQ,
    ERROR_MISS_NEXT_CLICK,
    ERROR_COIL_API,
    ERROR_SUPPLIER_API,
    ERROR_YARD_API,
    ERROR_FROM_API
}