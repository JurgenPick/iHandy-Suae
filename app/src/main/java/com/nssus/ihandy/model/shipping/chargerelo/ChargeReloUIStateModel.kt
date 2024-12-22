package com.nssus.ihandy.model.shipping.chargerelo

import androidx.annotation.StringRes
import com.nssus.ihandy.R

data class ChargeReloUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: ChargeReloNavigateType = ChargeReloNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: ChargeReloErrorModel? = null,
    //
    var coilNo: String = "", //
    var yyrrcct: String = "", //
    var width: String = "" //
)

enum class ChargeReloNavigateType {
    GO_TO_MAIN,
    GO_BACK,
    //    DISPLAY_CONFIRM_DIALOG,
//    DISPLAY_DOWNLOAD_SUCCESS_DIALOG,
//    BACK_TO_SELECT_OR_SEARCH_COMP,
    NONE
}

data class ChargeReloErrorModel(
    val chargeReloErrorType: ChargeReloErrorType = ChargeReloErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class ChargeReloErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_FROM_API
}