package com.nssus.ihandy.ui.common.inventorytaking.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.model.common.inventorytaking.InvTakingAction
import com.nssus.ihandy.model.common.inventorytaking.InvTakingErrorModel
import com.nssus.ihandy.model.common.inventorytaking.InvTakingErrorType
import com.nssus.ihandy.model.common.inventorytaking.InvTakingNavigateType
import com.nssus.ihandy.model.common.inventorytaking.InvTakingUIStateModel

class InvTakingViewModel(

) : ViewModel() {
    private val _invTakingUISt = mutableStateOf(InvTakingUIStateModel())
    val invTakingUISt: State<InvTakingUIStateModel> = _invTakingUISt

    fun action(viewAction: InvTakingAction) {
        when (viewAction) {
            is InvTakingAction.GoBack -> {
                _invTakingUISt.value = InvTakingUIStateModel()
                _invTakingUISt.value = onInvTakingUIStateSucccess(
                    navigateType = InvTakingNavigateType.GO_BACK
                )
            }

            is InvTakingAction.TypingYYRRCCTTextField -> { //
//                onYardEntryUIStateLoading()
                _invTakingUISt.value = onInvTakingUIStateSucccess().copy(
                    yyrrcct = viewAction.text
                )
            }
            is InvTakingAction.TypingCoilNoTextField -> { //
//                onYardEntryUIStateLoading()
                _invTakingUISt.value = onInvTakingUIStateSucccess().copy(
                    coilNo = viewAction.text
                )
            }
            is InvTakingAction.TypingSupplierNoTextField -> { //
//                onYardEntryUIStateLoading()
                _invTakingUISt.value = onInvTakingUIStateSucccess().copy(
                    supplier = viewAction.text
                )
            }
            is InvTakingAction.ClickClearButton -> {
//                onYardEntryUIStateLoading()
                _invTakingUISt.value = InvTakingUIStateModel()
                _invTakingUISt.value = onInvTakingUIStateSucccess().copy(
                    coilNo = "",
                    yyrrcct = "",
                    supplier = ""
                )
            }
        }
    }

    fun initNavigateData() {
        _invTakingUISt.value = onInvTakingUIStateSucccess()
    }

    private fun onInvTakingUIStateSucccess(
        navigateType: InvTakingNavigateType = InvTakingNavigateType.NONE,
        successMsg: String? = null
    ): InvTakingUIStateModel = _invTakingUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onInvTakingUIStateError(
        invTakingErrorType: InvTakingErrorType = InvTakingErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): InvTakingUIStateModel = _invTakingUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = InvTakingNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = InvTakingErrorModel(
            invTakingErrorType = invTakingErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onInvTakingUIStateLoading(isLoading: Boolean = true) {
        _invTakingUISt.value = _invTakingUISt.value.copy(isLoading = isLoading)
    }

}