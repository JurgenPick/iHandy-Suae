package com.nssus.ihandy.model.production.rcltolling

import androidx.annotation.StringRes
import com.nssus.ihandy.R


data class RCLTollingUIStateModel(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val isClearAllTextFieldValue: Boolean = false,
    val isAlreadyInitialized: Boolean = false,
    var isGetCoilNumberResp: Boolean = false,
    val message: String? = null,
    var successMsg: String? = null,
    var errorBody: RCLTollingErrorModel? = null,
    val navigateType: RCLTollingNavigateType = RCLTollingNavigateType.NONE,
    val coilNo: String = "",
    val cancelReturn: String = "",
    val yyrrccss: String = ""
)
enum class RCLTollingNavigateType {
    GO_BACK,
    DISPLAY_DIALOG_SUCCESS,
    NONE
}

data class RCLTollingErrorModel(
    val rclTollingErrorType: RCLTollingErrorType = RCLTollingErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class RCLTollingErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_E003(R.string.yard_entry_error_e003),
    ERROR_FROM_API
}