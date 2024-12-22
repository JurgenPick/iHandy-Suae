package com.nssus.ihandy.model.login

import androidx.annotation.StringRes
import com.nssus.ihandy.R

data class LoginUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: LoginNavigateType = LoginNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: LoginErrorModel? = null,
    var isNextUserNameAction : Boolean = false,
    var isNextPasswordAction : Boolean = false,
    var isClearTextField : Boolean = false
)

enum class LoginNavigateType {
    GO_TO_MAIN,
//    DISPLAY_WARNING_MSG_DIALOG,
//    DISPLAY_CONFIRM_DIALOG,
//    DISPLAY_DOWNLOAD_SUCCESS_DIALOG,
//    BACK_TO_SELECT_OR_SEARCH_COMP,
    NONE
}

data class LoginErrorModel(
    val loginErrorType: LoginErrorType = LoginErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class LoginErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
//    DATA_NOT_FOUND(R.string.base_data_not_found),
    EMPTY_FILL_USERNAME(R.string.login_empty_fill_username_warning_msg),
    EMPTY_FILL_PASSWORD(R.string.login_empty_fill_password_warning_msg),
    ERROR_FROM_API
}