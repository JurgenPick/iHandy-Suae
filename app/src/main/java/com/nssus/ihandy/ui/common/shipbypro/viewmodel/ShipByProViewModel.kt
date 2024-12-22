package com.nssus.ihandy.ui.common.shipbypro.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.data.extension.setSelectedRemoveFrom
import com.nssus.ihandy.data.usecase.common.shipbyproduct.ShipByProductUseCase
import com.nssus.ihandy.model.common.shipbypro.GetCheckCoilResponse
import com.nssus.ihandy.model.common.shipbypro.SendMqCoilListRequest
import com.nssus.ihandy.model.common.shipbypro.ShipByProAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProDialogAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProErrorModel
import com.nssus.ihandy.model.common.shipbypro.ShipByProErrorType
import com.nssus.ihandy.model.common.shipbypro.ShipByProNavigateType
import com.nssus.ihandy.model.common.shipbypro.ShipByProUIStateModel
import com.nssus.ihandy.model.network.NetworkResult
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingErrorType
import com.nssus.ihandy.ui.common.shipbypro.constant.ShipByProConstant.MAX_LENGTH_SHIPMENT_LOT
import kotlinx.coroutines.launch

class ShipByProViewModel (
    private val shipByProductUC : ShipByProductUseCase
): ViewModel(){
    private val _shipByProUISt = mutableStateOf(ShipByProUIStateModel())
    val shipByProUISt: State<ShipByProUIStateModel> = _shipByProUISt

    fun initData(){
        if (_shipByProUISt.value.isAlreadyInitialFeature) {return}

        _shipByProUISt.value = ShipByProUIStateModel(isAlreadyInitialFeature = true)
    }

    fun action(viewAction: ShipByProAction){
        when(viewAction){
            is ShipByProAction.GoBack -> {
                _shipByProUISt.value = ShipByProUIStateModel(isClearAllTextFieldValue = true)
                _shipByProUISt.value = onShipByProUIStateSuccess(navigateType = ShipByProNavigateType.GO_BACK)
            }

            //Input keyboard
            is ShipByProAction.TypingShipmentLotTextField -> {
                _shipByProUISt.value = _shipByProUISt.value.copy(
                    shipmentLot = viewAction.text
                )
            }
            is ShipByProAction.TypingCoilNumberTextField -> {
                _shipByProUISt.value = _shipByProUISt.value.copy(
                    coilNumber = viewAction.text
                )
            }

            //Set Init
            is ShipByProAction.SetInitFlagClearAllTextField ->{
                _shipByProUISt.value = onShipByProUIStateSuccess().copy(
                    isClearAllTextFieldValue = false
                )
            }
            is ShipByProAction.SetInitFlagGetShipmentLotRespSuccess -> {
                _shipByProUISt.value = onShipByProUIStateSuccess().copy(
                    isGetShipmentLotRespSuccess = false
                )
            }
            is ShipByProAction.SetInitFlagGetCoilNumberResp ->{
                _shipByProUISt.value = onShipByProUIStateSuccess().copy(
                    isGetCoilNumberResp = false,
                )
            }

            //Next action
            is ShipByProAction.ClickNextActionShipmentLotTextField -> { callCheckShip() }

            is ShipByProAction.ClickNextActionCoilNumberTextField -> { callCheckCoil() }

            is ShipByProAction.ClickButtonCountCoil -> {
                if (_shipByProUISt.value.resCoilList.isEmpty()){
                    _shipByProUISt.value = onShipByProUIStateError(
                        shipByProErrorType = ShipByProErrorType.EMPTY_COIL_LIST
                    )
                    return
                }
                onShipByProUIStateLoading()
                _shipByProUISt.value = onShipByProUIStateSuccess(
                    navigateType = ShipByProNavigateType.GO_TO_COIL_DETAIL_LS
                )
            }
            is ShipByProAction.SelectCoil -> {
                _shipByProUISt.value = onShipByProUIStateSuccess(
                    navigateType = ShipByProNavigateType.DISPLAY_SHOW_REMOVE_COIL_DIALOG
                ).copy(
                    resCoilList = _shipByProUISt.value.resCoilList.setSelectedRemoveFrom(viewAction.selectData)
                )
            }
            is ShipByProAction.ClickBackToShipByPro -> {
                _shipByProUISt.value = onShipByProUIStateSuccess(
                    navigateType = ShipByProNavigateType.BACK_TO_SHIP_BY_PRO_MAIN
                )
            }

            is ShipByProAction.InitNavigateData -> initNavigateData()
        }
    }

    fun actionFormDialog(dialogAction: ShipByProDialogAction){
        when(dialogAction){
            is ShipByProDialogAction.ClickContinueDialogButton ->{
                _shipByProUISt.value = ShipByProUIStateModel(
                    isClearAllTextFieldValue = true,
                    isAlreadyInitialFeature = true
                    )
            }
            is ShipByProDialogAction.ClickSendButton -> {
                if (_shipByProUISt.value.shipmentLot.isEmpty()){
                    _shipByProUISt.value = onShipByProUIStateError(
                        shipByProErrorType = ShipByProErrorType.EMPTY_SHIPMENT_LOT
                    )
                    return
                }
                if (_shipByProUISt.value.resCoilList.isEmpty()) {
                    _shipByProUISt.value = onShipByProUIStateError(
                        shipByProErrorType = ShipByProErrorType.EMPTY_COIL_LIST
                    )
                    return
                }

                _shipByProUISt.value = onShipByProUIStateSuccess(
                    navigateType = ShipByProNavigateType.DISPLAY_DIALOG_SEND_MQ,
                    successMsg = "คุณต้องการส่งข้อมูลหรือ ไม่"
                )

            }
            is ShipByProDialogAction.ClickConfirmSendMQ -> { callSendMQ() }
            is ShipByProDialogAction.ClickClearAllButton-> {
                _shipByProUISt.value = onShipByProUIStateSuccess(
                    navigateType = ShipByProNavigateType.DISPLAY_DIALOG_WARNING,
                    successMsg = "ต้องการลบข้อมูลทั้งหมดหรือไม่"
                )
            }
            is ShipByProDialogAction.ClickLeftShipByProDialogButton -> {
                when(_shipByProUISt.value.errorBody?.shipByProErrorType){
                    ShipByProErrorType.ERROR_FROM_API_SHIPMENT_LOT,
                    ShipByProErrorType.DIGIT_SHIPMENT_LOT,
                    ShipByProErrorType.EMPTY_SHIPMENT_LOT ->{
                        _shipByProUISt.value = ShipByProUIStateModel(
                            isClearAllTextFieldValue = true,
                            isAlreadyInitialFeature = true
                        )
                    }
                    ShipByProErrorType.EMPTY_COIL_NUMBER,
                    ShipByProErrorType.ERROR_FROM_API_COIL_NO,
                    ShipByProErrorType.DUPLICATE_COIL_NUMBER,
                    ShipByProErrorType.EMPTY_COIL_LIST
                    -> {
                        _shipByProUISt.value = onShipByProUIStateSuccess().copy(
                            coilNumber = "",
                            isGetCoilNumberResp = true
                        )
                    }
                    else -> action(ShipByProAction.InitNavigateData)
                }
            }
            is ShipByProDialogAction.ClickLeftDeleteCoilDialogButton -> {
                _shipByProUISt.value =onShipByProUIStateSuccess().copy(
                    resCoilList = _shipByProUISt.value.resCoilList.apply {
                        removeIf { it.isSelectedRemove }
                    },
                    sumCoilWt = _shipByProUISt.value.resCoilList.sumOf { it.coilWeight ?: 0.0 }
                )
            }
            is ShipByProDialogAction.ClickRightDeleteCoilDialogButton -> {
                _shipByProUISt.value = onShipByProUIStateSuccess().copy(
                    resCoilList = _shipByProUISt.value.resCoilList.apply {
                        find { it.isSelectedRemove }?.let { it.isSelectedRemove = false }
                    }
                )
            }
            else->{}
        }
    }

    fun initNavigateData() {
        _shipByProUISt.value = onShipByProUIStateSuccess()
    }

    private fun callCheckShip() {
        if (_shipByProUISt.value.shipmentLot.isEmpty() ){
            _shipByProUISt.value = onShipByProUIStateError(
                shipByProErrorType = ShipByProErrorType.EMPTY_SHIPMENT_LOT
            )
            return
        }
        if (_shipByProUISt.value.shipmentLot.length < MAX_LENGTH_SHIPMENT_LOT-1){
            _shipByProUISt.value = onShipByProUIStateError(
                shipByProErrorType = ShipByProErrorType.DIGIT_SHIPMENT_LOT
            )
            return
        }
        viewModelScope.launch {
            shipByProductUC.getCheckShipmentLot(_shipByProUISt.value.shipmentLot).collect{
                when (it){
                    is NetworkResult.Success -> {
                        _shipByProUISt.value = onShipByProUIStateSuccess().copy(
                            isGetShipmentLotRespSuccess = true
                        )
                    }
                    is NetworkResult.Loading -> { onShipByProUIStateLoading() }
                    is NetworkResult.Error -> {
                        _shipByProUISt.value = onShipByProUIStateError(
                            shipByProErrorType = ShipByProErrorType.ERROR_FROM_API_SHIPMENT_LOT,
                            errorMsg = it.errorMessage
                        )
                    }
                }
            }
        }
    }

    private fun callCheckCoil(){
        if (_shipByProUISt.value.shipmentLot.isEmpty() ){
            _shipByProUISt.value = onShipByProUIStateError(
                shipByProErrorType = ShipByProErrorType.EMPTY_SHIPMENT_LOT,
            )
            return
        }
        if (_shipByProUISt.value.shipmentLot.length < MAX_LENGTH_SHIPMENT_LOT-1){
            _shipByProUISt.value = onShipByProUIStateError(
                shipByProErrorType = ShipByProErrorType.DIGIT_SHIPMENT_LOT,
            )
            return
        }
        if (_shipByProUISt.value.coilNumber.isEmpty()){
            _shipByProUISt.value = onShipByProUIStateError(
                shipByProErrorType = ShipByProErrorType.EMPTY_COIL_NUMBER
            )
            return
        }
        if (_shipByProUISt.value.resCoilList.find { it.coilNo == _shipByProUISt.value.coilNumber } != null){
            _shipByProUISt.value = onShipByProUIStateError(
                shipByProErrorType = ShipByProErrorType.DUPLICATE_COIL_NUMBER
            )
            return
        }
        if (_shipByProUISt.value.shipmentLot.first() == 'X'){
            _shipByProUISt.value = onShipByProUIStateSuccess().copy(
                resCoilList = _shipByProUISt.value.resCoilList.apply {
                    add(GetCheckCoilResponse(coilNo = _shipByProUISt.value.coilNumber))
                },
                coilNumber = "",
                isGetCoilNumberResp = true
            )
            println(_shipByProUISt.value.resCoilList)
            return
        }
        viewModelScope.launch {
            shipByProductUC.getCheckCoilNumber(_shipByProUISt.value.shipmentLot , _shipByProUISt.value.coilNumber).collect { it ->
                when (it){
                    is NetworkResult.Success -> {
                        _shipByProUISt.value.resCoilList.add(
                            it.data ?: GetCheckCoilResponse()
                        )
                        _shipByProUISt.value = onShipByProUIStateSuccess().copy(
                            lastCoilWeight = _shipByProUISt.value.resCoilList.last().coilWeight ?: 0.0,
                            lastCoil = _shipByProUISt.value.resCoilList.last().coilNo ?: "",
                            shipSt = _shipByProUISt.value.resCoilList.last().shipStatus ?: "",
                            sumCoilWt = _shipByProUISt.value.resCoilList.sumOf { it.coilWeight ?:0.0 },
                            coilNumber = "",
                            isGetCoilNumberResp = true
                        )

                    }
                    is NetworkResult.Loading -> {onShipByProUIStateLoading()}
                    is NetworkResult.Error -> {
                        _shipByProUISt.value = onShipByProUIStateError(
                            shipByProErrorType = ShipByProErrorType.ERROR_FROM_API_COIL_NO,
                            errorMsg = it.errorMessage
                        )
                    }
                }
            }
        }
    }

    private fun callSendMQ() {

        val sendMqCoilListRequest = SendMqCoilListRequest(
            shipmentLotNo = _shipByProUISt.value.shipmentLot,
            listCoilNo = _shipByProUISt.value.resCoilList.map { it.coilNo ?: "" }
        )
        println(sendMqCoilListRequest)
        viewModelScope.launch {
            shipByProductUC.sendMQ(sendMqCoilListRequest).collect{ it ->
                when(it){
                    is NetworkResult.Success -> {
                        _shipByProUISt.value = onShipByProUIStateSuccess(
                            navigateType = ShipByProNavigateType.DISPLAY_DIALOG_SUCCESS,
                            successMsg = "ส่งข้อมูลสำเร็จ"
                        )
                    }
                    is NetworkResult.Loading ->{onShipByProUIStateLoading()}
                    is NetworkResult.Error ->{
                        _shipByProUISt.value = onShipByProUIStateError(
                            shipByProErrorType = ShipByProErrorType.ERROR_FROM_API_SEND_MQ,
                            errorMsg = it.errorMessage
                        )
                        println(it.errorMessage)
                    }
                }
            }
        }


    }

    private fun onShipByProUIStateSuccess(
        navigateType: ShipByProNavigateType =ShipByProNavigateType.NONE,
        successMsg: String? = null
    ): ShipByProUIStateModel =_shipByProUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        message = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onShipByProUIStateError(
        shipByProErrorType: ShipByProErrorType = ShipByProErrorType.ERROR_FROM_API,
        errorMsg: String? = null,
    ): ShipByProUIStateModel =_shipByProUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = ShipByProNavigateType.NONE,
        message = null,
        isError = true,
        errorBody = ShipByProErrorModel(
            shipByProErrorType = shipByProErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onShipByProUIStateLoading(isLoading: Boolean = true){
        _shipByProUISt.value = _shipByProUISt.value.copy(isLoading = isLoading)
    }
}