package com.nssus.ihandy.ui.common.relabel.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.data.extension.getSelectedItem
import com.nssus.ihandy.data.extension.getSelectedItemValue
import com.nssus.ihandy.data.extension.setSelectItemFrom
import com.nssus.ihandy.model.common.relabel.ReLabelAction
import com.nssus.ihandy.model.common.relabel.ReLabelErrorModel
import com.nssus.ihandy.model.common.relabel.ReLabelErrorType
import com.nssus.ihandy.model.common.relabel.ReLabelNavigateType
import com.nssus.ihandy.model.common.relabel.ReLabelUIStateModel
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloUIStateModel
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.ui.common.relabel.util.ReLabelUtil.getDataLs

class ReLabelViewModel(

) : ViewModel() {
    private val _relabelUISt = mutableStateOf(ReLabelUIStateModel())
    val relabelUISt: State<ReLabelUIStateModel> = _relabelUISt

    fun initData() {
        _relabelUISt.value = ReLabelUIStateModel().copy(
            dataLs = getDataLs()
        )
    }

    fun action(viewAction: ReLabelAction) {
        when (viewAction) {
            is ReLabelAction.GoBack -> {
                _relabelUISt.value = ReLabelUIStateModel()
                _relabelUISt.value = onReLabelUIStateSuccess(
                    navigateType = ReLabelNavigateType.GO_BACK
                )
            }
            is ReLabelAction.TypingReLabel1TextField -> { //
//                onReLabelUIStateLoading()
                _relabelUISt.value = onReLabelUIStateSuccess().copy(
                    relabelNo1 = viewAction.text
                )
            }
            is ReLabelAction.TypingReLabel2TextField -> { //
//                onReLabelUIStateLoading()
                _relabelUISt.value = onReLabelUIStateSuccess().copy(
                    relabelNo2 = viewAction.text
                )
            }

            is ReLabelAction.ClickSendButton -> {
                println("SSSS: ${_relabelUISt.value.relabelNo1} ${_relabelUISt.value.relabelNo2} ")
                println("DROPP display: ${_relabelUISt.value.dataLs.getSelectedItem()?.display} ")
                println("DROPP display: ${_relabelUISt.value.dataLs.getSelectedItemValue()} ")
            }
            is ReLabelAction.ClickClearButton -> {
//                onrelabelUIStateLoading()
                _relabelUISt.value = ReLabelUIStateModel()
                _relabelUISt.value = onReLabelUIStateSuccess().copy(
                    relabelNo1 = "",
                    relabelNo2 = ""
                )
            }
            is ReLabelAction.SelectDataDropdown -> {
                selectDataDropdown(viewAction.selectedData)
            }
        }
    }

    private fun selectDataDropdown(selectedData: DropdownUIModel) {
        onReLabelUIStateLoading()

        _relabelUISt.value.dataLs.setSelectItemFrom(selectedData)

        initNavigateData()
    }

    fun initNavigateData() {
        _relabelUISt.value = onReLabelUIStateSuccess()
    }

    private fun onReLabelUIStateSuccess(
        navigateType: ReLabelNavigateType = ReLabelNavigateType.NONE,
        successMsg: String? = null
    ): ReLabelUIStateModel = _relabelUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onReLabelUIStateError(
        relabelErrorType: ReLabelErrorType = ReLabelErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): ReLabelUIStateModel = _relabelUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = ReLabelNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = ReLabelErrorModel(
            relabelErrorType = relabelErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onReLabelUIStateLoading(isLoading: Boolean = true) {
        _relabelUISt.value = _relabelUISt.value.copy(isLoading = isLoading)
    }

    
}