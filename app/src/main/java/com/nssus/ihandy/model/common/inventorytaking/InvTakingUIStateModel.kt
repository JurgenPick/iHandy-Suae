package com.nssus.ihandy.model.common.inventorytaking

import androidx.annotation.StringRes
import com.nssus.ihandy.R

data class InvTakingUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: InvTakingNavigateType = InvTakingNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: InvTakingErrorModel? = null,
    //
    var coilNo: String = "", //
    var yyrrcct: String = "", //
    var supplier: String = "" //
)

enum class InvTakingNavigateType {
    GO_TO_MAIN,
    GO_BACK,
    //    DISPLAY_CONFIRM_DIALOG,
//    DISPLAY_DOWNLOAD_SUCCESS_DIALOG,
//    BACK_TO_SELECT_OR_SEARCH_COMP,
    NONE
}

data class InvTakingErrorModel(
    val invTakingErrorType: InvTakingErrorType = InvTakingErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class InvTakingErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_FROM_API
}