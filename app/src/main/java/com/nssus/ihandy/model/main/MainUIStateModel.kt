package com.nssus.ihandy.model.main

import androidx.annotation.StringRes
import com.nssus.ihandy.R

data class MainUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: MainNavigateType = MainNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: MainErrorModel? = null
)

enum class MainNavigateType {
    GO_TO_RESTART_APP,
    NONE
}

data class MainErrorModel(
    val mainErrorType: MainErrorType = MainErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class MainErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_FROM_API
}