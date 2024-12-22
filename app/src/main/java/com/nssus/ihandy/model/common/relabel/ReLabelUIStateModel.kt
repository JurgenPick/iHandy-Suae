package com.nssus.ihandy.model.common.relabel

import androidx.annotation.StringRes
import com.nssus.ihandy.R
import com.nssus.ihandy.model.ui.DropdownUIModel

data class ReLabelUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: ReLabelNavigateType = ReLabelNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: ReLabelErrorModel? = null,
    //
    var dataLs: List<DropdownUIModel> = emptyList(),
    var relabelNo1: String = "", //
    var relabelNo2: String = "", //
)

enum class ReLabelNavigateType {
    GO_TO_MAIN,
    GO_BACK,
//    DISPLAY_CONFIRM_DIALOG,
//    DISPLAY_DOWNLOAD_SUCCESS_DIALOG,
//    BACK_TO_SELECT_OR_SEARCH_COMP,
    NONE
}

data class ReLabelErrorModel(
    val relabelErrorType: ReLabelErrorType = ReLabelErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class ReLabelErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_FROM_API
}