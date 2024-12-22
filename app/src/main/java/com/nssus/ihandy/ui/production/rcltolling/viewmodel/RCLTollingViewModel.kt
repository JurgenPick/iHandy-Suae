package com.nssus.ihandy.ui.production.rcltolling.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.model.production.cvloading.CVLoadingErrorModel
import com.nssus.ihandy.model.production.cvloading.CVLoadingErrorType
import com.nssus.ihandy.model.production.cvloading.CVLoadingNavigateType
import com.nssus.ihandy.model.production.cvloading.CVLoadingUIStateModel
import com.nssus.ihandy.model.production.rcltolling.RCLTollingAction
import com.nssus.ihandy.model.production.rcltolling.RCLTollingDialogAction
import com.nssus.ihandy.model.production.rcltolling.RCLTollingErrorModel
import com.nssus.ihandy.model.production.rcltolling.RCLTollingErrorType
import com.nssus.ihandy.model.production.rcltolling.RCLTollingUIStateModel
import com.nssus.ihandy.model.production.rcltolling.RCLTollingNavigateType
class RCLTollingViewModel : ViewModel() {
    private val _rclTollingUISt = mutableStateOf(RCLTollingUIStateModel())
    val rclTollingUISt: State<RCLTollingUIStateModel> = _rclTollingUISt
    // Initialize data when the screen is loaded
    fun initData() {
        _rclTollingUISt.value = RCLTollingUIStateModel(isAlreadyInitialized = true)
    }
    // Handle main actions
    fun action(action: RCLTollingAction) {
        when (action) {
            is RCLTollingAction.GoBack -> {
                _rclTollingUISt.value = RCLTollingUIStateModel(isClearAllTextFieldValue = true)
                _rclTollingUISt.value = onRCLTollingUIStateSuccess(
                    navigateType = RCLTollingNavigateType.GO_BACK
                )
            }
            is RCLTollingAction.TypingCoilNumberTextField -> {
                _rclTollingUISt.value = _rclTollingUISt.value.copy(coilNo = action.text)
            }
            is RCLTollingAction.InitNavigateData -> {
                _rclTollingUISt.value = _rclTollingUISt.value.copy(navigateType = RCLTollingNavigateType.NONE)
            }
        }
    }
    // Handle dialog-specific actions
    fun actionFormDialog(dialogAction: RCLTollingDialogAction) {
        when (dialogAction) {
            is RCLTollingDialogAction.ClickSendButton -> {
                _rclTollingUISt.value = _rclTollingUISt.value.copy(
                    navigateType = RCLTollingNavigateType.DISPLAY_DIALOG_SUCCESS,
                    message = "Data sent successfully."
                )
            }
            is RCLTollingDialogAction.ClickClearAllButton -> {
                _rclTollingUISt.value = _rclTollingUISt.value.copy(isClearAllTextFieldValue = true)
            }
        }
    }

    private fun onRCLTollingUIStateSuccess(
        navigateType: RCLTollingNavigateType = RCLTollingNavigateType.NONE,
        successMsg: String? = null
    ): RCLTollingUIStateModel = _rclTollingUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onRCLTollingUIStateError(
        rclTollingErrorType: RCLTollingErrorType = RCLTollingErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): RCLTollingUIStateModel = _rclTollingUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = RCLTollingNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = RCLTollingErrorModel(
            rclTollingErrorType = rclTollingErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onRCLTollingUIStateLoading(isLoading: Boolean = true) {
        _rclTollingUISt.value = _rclTollingUISt.value.copy(isLoading = isLoading)
    }
}