package com.nssus.ihandy.model.production.cvloading

import androidx.annotation.StringRes
import com.nssus.ihandy.R

data class CVLoadingUIStateModel(
    //Flag (initial before do the logic inside)

    var isGetCoilNumberResp: Boolean = false,
    var isGetLineSkidResp: Boolean = false,
    var isGetSupplierNumberResp: Boolean = false,
    var isClearAllTextFieldValue: Boolean = false,


    var isAlreadyInitialFeature : Boolean = false,
    var message: String? = null,
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: CVLoadingNavigateType = CVLoadingNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: CVLoadingErrorModel? = null,
    var isClickNextActionCoilNo: Boolean =false,
    var atErrorInCVLoadingTextBox : Boolean = false,
    //
    var coilNo: String = "", //
    var lcsNo: String = "", //
    var supplierNo: String = "" //
)

enum class CVLoadingNavigateType {
    GO_TO_MAIN,
    GO_BACK,
    DISPLAY_CLEAR_ALL_DIALOG,
    DISPLAY_DIALOG_SEND_MQ,
    DISPLAY_DIALOG_WARNING,
    DISPLAY_DIALOG_SUCCESS,
//    DISPLAY_CONFIRM_DIALOG,
//    DISPLAY_DOWNLOAD_SUCCESS_DIALOG,
//    BACK_TO_SELECT_OR_SEARCH_COMP,
    NONE
}

data class CVLoadingErrorModel(
    val cvLoadingErrorType: CVLoadingErrorType = CVLoadingErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class CVLoadingErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_E003(R.string.yard_entry_error_e003),
    ERROR_FROM_API
}
