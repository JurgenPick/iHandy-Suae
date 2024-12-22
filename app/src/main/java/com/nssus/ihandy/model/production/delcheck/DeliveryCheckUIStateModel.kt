package com.nssus.ihandy.model.production.delcheck

import androidx.annotation.StringRes
import com.nssus.ihandy.R
import com.nssus.ihandy.model.production.cvloading.CVLoadingErrorModel

data class DeliveryCheckUIStateModel(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    //Flag (initial before do the logic inside)
    var isClearAllTextFieldValue: Boolean = false,
    var isGetCoilNumberResp: Boolean = false,
    val isAlreadyInitialized: Boolean = false,
    var successMsg: String? = null,
    var errorBody: DeliveryCheckErrorModel? = null,
    val message: String? = null,
    val navigateType: DeliveryCheckNavigateType = DeliveryCheckNavigateType.NONE,

    val coilNo: String = "",
    val lastFiveCoils: List<CoilItem> = emptyList()
) {
    data class CoilItem(
        val coilNumber: String,
        val status: String,
        val isMatched: Boolean
    )
}
enum class DeliveryCheckNavigateType {
    GO_BACK,
    DISPLAY_DIALOG_SUCCESS,
    NONE
}

data class DeliveryCheckErrorModel(
    val deliveryCheckErrorType: DeliveryCheckErrorType = DeliveryCheckErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class DeliveryCheckErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_E003(R.string.yard_entry_error_e003),
    ERROR_FROM_API
}