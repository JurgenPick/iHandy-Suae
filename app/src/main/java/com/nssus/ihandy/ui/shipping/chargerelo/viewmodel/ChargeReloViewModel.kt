package com.nssus.ihandy.ui.shipping.chargerelo.viewmodel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloAction
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloErrorModel
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloErrorType
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloNavigateType
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloUIStateModel

class ChargeReloViewModel (

): ViewModel() {
    private val _chargeReloUISt = mutableStateOf(ChargeReloUIStateModel())
    val chargeReloUISt: State<ChargeReloUIStateModel> = _chargeReloUISt

    fun action(viewAction: ChargeReloAction) {
        when (viewAction) {
            is ChargeReloAction.GoBack -> {
                _chargeReloUISt.value = ChargeReloUIStateModel()
                _chargeReloUISt.value = onChargeReloUIStateSucccess(
                    navigateType = ChargeReloNavigateType.GO_BACK
                )
            }
            is ChargeReloAction.TypingCoilNoTextField -> { //
//                onYardEntryUIStateLoading()
                _chargeReloUISt.value = onChargeReloUIStateSucccess().copy(
                    coilNo = viewAction.text
                )
            }
            is ChargeReloAction.TypingYYRRCCTTextField -> { //
//                onYardEntryUIStateLoading()
                _chargeReloUISt.value = onChargeReloUIStateSucccess().copy(
                    yyrrcct = viewAction.text
                )
            }
            is ChargeReloAction.TypingWidthNoTextField -> { //
//                onYardEntryUIStateLoading()
                _chargeReloUISt.value = onChargeReloUIStateSucccess().copy(
                    width = viewAction.text
                )
            }
            is ChargeReloAction.ClickSendButton -> {
                println("SSSS: ${_chargeReloUISt.value.coilNo} ${_chargeReloUISt.value.yyrrcct} ${_chargeReloUISt.value.width}")
            }
            is ChargeReloAction.ClickClearButton -> {
//                onYardEntryUIStateLoading()
                _chargeReloUISt.value = ChargeReloUIStateModel()
                _chargeReloUISt.value = onChargeReloUIStateSucccess().copy(
                    coilNo = "",
                    yyrrcct = "",
                    width = ""
                )
            }
        }
    }

    fun initNavigateData() {
        _chargeReloUISt.value = onChargeReloUIStateSucccess()
    }

    private fun onChargeReloUIStateSucccess(
        navigateType: ChargeReloNavigateType = ChargeReloNavigateType.NONE,
        successMsg: String? = null
    ): ChargeReloUIStateModel = _chargeReloUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onChargeReloUIStateError(
        chargeReloErrorType: ChargeReloErrorType = ChargeReloErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): ChargeReloUIStateModel = _chargeReloUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = ChargeReloNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = ChargeReloErrorModel(
            chargeReloErrorType = chargeReloErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onChargeReloUIStateLoading(isLoading: Boolean = true) {
        _chargeReloUISt.value = _chargeReloUISt.value.copy(isLoading = isLoading)
    }
}