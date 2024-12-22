package com.nssus.ihandy.model.home

import androidx.annotation.StringRes
import com.nssus.ihandy.R

data class HomeUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: HomeNavigateType = HomeNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: HomeErrorModel? = null,
    // other values
//    var menuData: DisplayHomeModel = DisplayHomeModel(),
    var selectedRoute: String = "",
)

enum class HomeNavigateType {
    GO_TO_SELECTED_MENU,
    NONE
}

data class HomeErrorModel(
    val homeErrorType: HomeErrorType = HomeErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class HomeErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_FROM_API
}