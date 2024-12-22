package com.nssus.ihandy.model.common.validate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nssus.ihandy.R

data class ValidateUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: ValidateNavigateType = ValidateNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: ValidateErrorModel? = null,

    var isClearAllTextFieldValue: Boolean = false,

    // NextAction
    var isGetValidate1RespSuccess: Boolean = false,
    var isGetValidate2RespSuccess: Boolean = false,
    var isGetValidate3RespSuccess: Boolean = false,
    //
    var isvalidateNo1TfError: Boolean = false,
    var isvalidateNo2TfError: Boolean = false,
    var isvalidateNo3TfError: Boolean = false,
    @DrawableRes var resultIconId: Int? = null,
    var validateNo1: String = "", //
    var validateNo2: String = "", //
    var validateNo3: String = "", //
    @StringRes var validateResultId: Int? = null
)

enum class ValidateNavigateType {
    GO_TO_MAIN,
    GO_BACK,
//    DISPLAY_CONFIRM_DIALOG,
//    DISPLAY_DOWNLOAD_SUCCESS_DIALOG,
//    BACK_TO_SELECT_OR_SEARCH_COMP,
    NONE
}

data class ValidateErrorModel(
    val validateErrorType: ValidateErrorType = ValidateErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class ValidateErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_FROM_API
}