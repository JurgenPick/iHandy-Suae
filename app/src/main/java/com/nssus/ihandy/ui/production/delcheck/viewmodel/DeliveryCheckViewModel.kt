package com.nssus.ihandy.ui.production.delcheck.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.model.production.cvloading.CVLoadingErrorModel
import com.nssus.ihandy.model.production.cvloading.CVLoadingErrorType
import com.nssus.ihandy.model.production.cvloading.CVLoadingNavigateType
import com.nssus.ihandy.model.production.cvloading.CVLoadingUIStateModel
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckAction
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckDialogAction
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckErrorModel
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckErrorType
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckUIStateModel
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckNavigateType
class DeliveryCheckViewModel : ViewModel() {
    private val _deliveryCheckUISt = mutableStateOf(DeliveryCheckUIStateModel())
    val deliveryCheckUISt: State<DeliveryCheckUIStateModel> = _deliveryCheckUISt
    // Initialize data when the screen is loaded
    fun initData() {
        _deliveryCheckUISt.value = DeliveryCheckUIStateModel(
            lastFiveCoils = listOf(
                DeliveryCheckUIStateModel.CoilItem("Coil12345", "Yes", true),
                DeliveryCheckUIStateModel.CoilItem("Coil67890", "No", false),
                DeliveryCheckUIStateModel.CoilItem("Coil54321", "No", false),
                DeliveryCheckUIStateModel.CoilItem("Coil09876", "No", false),
                DeliveryCheckUIStateModel.CoilItem("Coil11223", "Yes", true)
            )
        )
    }
    // Handle main actions
    fun action(action: DeliveryCheckAction) {
        when (action) {
            is DeliveryCheckAction.GoBack -> {
                _deliveryCheckUISt.value = DeliveryCheckUIStateModel(isClearAllTextFieldValue = true)
                _deliveryCheckUISt.value = onDeliveryCheckUIStateSuccess(
                    navigateType = DeliveryCheckNavigateType.GO_BACK
                )
            }
            is DeliveryCheckAction.TypingCoilNumberTextField -> {
                _deliveryCheckUISt.value = _deliveryCheckUISt.value.copy(coilNo = action.text)
            }
            is DeliveryCheckAction.InitNavigateData -> {
                _deliveryCheckUISt.value = _deliveryCheckUISt.value.copy(navigateType = DeliveryCheckNavigateType.NONE)
            }
        }
    }
    // Handle dialog-specific actions
    fun actionFormDialog(dialogAction: DeliveryCheckDialogAction) {
        when (dialogAction) {
            is DeliveryCheckDialogAction.ClickSendButton -> {
                _deliveryCheckUISt.value = _deliveryCheckUISt.value.copy(
                    navigateType = DeliveryCheckNavigateType.DISPLAY_DIALOG_SUCCESS,
                    message = "Data sent successfully."
                )
            }
            is DeliveryCheckDialogAction.ClickClearAllButton -> {
                _deliveryCheckUISt.value = _deliveryCheckUISt.value.copy(isClearAllTextFieldValue = true)
            }
        }
    }

    private fun onDeliveryCheckUIStateSuccess(
        navigateType: DeliveryCheckNavigateType = DeliveryCheckNavigateType.NONE,
        successMsg: String? = null
    ): DeliveryCheckUIStateModel = _deliveryCheckUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onDeliveryCheckUIStateError(
        deliveryCheckErrorType: DeliveryCheckErrorType = DeliveryCheckErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): DeliveryCheckUIStateModel = _deliveryCheckUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = DeliveryCheckNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = DeliveryCheckErrorModel(
            deliveryCheckErrorType = deliveryCheckErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onDeliveryCheckUIStateLoading(isLoading: Boolean = true) {
        _deliveryCheckUISt.value = _deliveryCheckUISt.value.copy(isLoading = isLoading)
    }
}