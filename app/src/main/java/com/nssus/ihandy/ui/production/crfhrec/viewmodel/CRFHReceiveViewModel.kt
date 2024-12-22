package com.nssus.ihandy.ui.production.crfhrec.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.model.common.shipbypro.ShipByProNavigateType
import com.nssus.ihandy.model.common.shipbypro.ShipByProUIStateModel
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveAction
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveDialogAction
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveErrorModel
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveErrorType
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveUIStateModel
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveNavigateType
import com.nssus.ihandy.model.production.cvloading.CVLoadingAction
import com.nssus.ihandy.model.production.cvloading.CVLoadingErrorModel
import com.nssus.ihandy.model.production.cvloading.CVLoadingErrorType
import com.nssus.ihandy.model.production.cvloading.CVLoadingNavigateType
import com.nssus.ihandy.model.production.cvloading.CVLoadingUIStateModel

class CRFHReceiveViewModel : ViewModel() {
    private val _crfhReceiveUISt = mutableStateOf(CRFHReceiveUIStateModel())
    val crfhReceiveUISt: State<CRFHReceiveUIStateModel> = _crfhReceiveUISt
    // Initialize data when the screen is loaded
    fun initData() {
        _crfhReceiveUISt.value = CRFHReceiveUIStateModel(isAlreadyInitialized = true)
    }
    // Handle main actions
    fun action(action: CRFHReceiveAction) {
        when (action) {
            is CRFHReceiveAction.GoBack -> {
                _crfhReceiveUISt.value = CRFHReceiveUIStateModel(isClearAllTextFieldValue = true)
                _crfhReceiveUISt.value = onCRFHReceiveStateSuccess(navigateType = CRFHReceiveNavigateType.GO_BACK)
            }
            is CRFHReceiveAction.TypingCoilNumberTextField -> {
                _crfhReceiveUISt.value = _crfhReceiveUISt.value.copy(coilNo = action.text)
            }
            is CRFHReceiveAction.TypingYYRRCCSSTextField -> {
                _crfhReceiveUISt.value = _crfhReceiveUISt.value.copy(yyrrccss = action.text)
            }
            is CRFHReceiveAction.TypingSupplierNoTextField -> {
                _crfhReceiveUISt.value = _crfhReceiveUISt.value.copy(supplierNo = action.text)
            }
            is CRFHReceiveAction.InitNavigateData -> {
                _crfhReceiveUISt.value = _crfhReceiveUISt.value.copy(navigateType = CRFHReceiveNavigateType.NONE)
            }
        }
    }
    // Handle dialog-specific actions
    fun actionFormDialog(dialogAction: CRFHReceiveDialogAction) {
        when (dialogAction) {
            is CRFHReceiveDialogAction.ClickSendButton -> {
                _crfhReceiveUISt.value = _crfhReceiveUISt.value.copy(
                    navigateType = CRFHReceiveNavigateType.DISPLAY_DIALOG_SUCCESS,
                    message = "Data sent successfully."
                )
            }
            is CRFHReceiveDialogAction.ClickClearAllButton -> {
                _crfhReceiveUISt.value = _crfhReceiveUISt.value.copy(isClearAllTextFieldValue = true)
            }
        }
    }

    private fun onCRFHReceiveStateSuccess(
        navigateType: CRFHReceiveNavigateType = CRFHReceiveNavigateType.NONE,
        successMsg: String? = null
    ): CRFHReceiveUIStateModel = _crfhReceiveUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        message = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onCRFHReceiveStateError(
        crfhReceiveErrorType: CRFHReceiveErrorType = CRFHReceiveErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): CRFHReceiveUIStateModel = _crfhReceiveUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = CRFHReceiveNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = CRFHReceiveErrorModel(
            crfhReceiveErrorType = crfhReceiveErrorType,
            errorMsg = errorMsg
        )
    )
}