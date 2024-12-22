package com.nssus.ihandy.ui.shipping.pcshipping.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.data.extension.formatDouble
import com.nssus.ihandy.data.extension.getMatchedItemWithCoilNumber
import com.nssus.ihandy.data.extension.setMatchedItemFrom
import com.nssus.ihandy.data.usecase.shipping.PcShippingUseCase
import com.nssus.ihandy.model.network.NetworkResult
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingAction
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingDialogAction
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingErrorModel
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingErrorType
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingNavigateType
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingUIStateModel
import com.nssus.ihandy.ui.shipping.pcshipping.constant.PcShippingConstant.COUNT_DOWN_COIL
import com.nssus.ihandy.ui.shipping.pcshipping.constant.PcShippingConstant.COUNT_DOWN_TRUCK
import com.nssus.ihandy.ui.shipping.pcshipping.constant.PcShippingConstant.COUNT_DOWN_WIDTH
import com.nssus.ihandy.ui.shipping.pcshipping.constant.PcShippingConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.shipping.pcshipping.constant.PcShippingConstant.MAX_LENGTH_SHIPMENT_LOT
import com.nssus.ihandy.ui.shipping.pcshipping.util.PcShippingUtil.mapToCoilDetail
import kotlinx.coroutines.launch

class PcShippingViewModel(
    private val pcShippingUC : PcShippingUseCase
) : ViewModel(){
    private val _pcShippingUISt = mutableStateOf(PcShippingUIStateModel())
    val pcShippingUISt : State<PcShippingUIStateModel> = _pcShippingUISt

    fun initData() {
        _pcShippingUISt.value = PcShippingUIStateModel()
    }

    fun action(viewAction: PcShippingAction){
        when(viewAction){
            is PcShippingAction.GoBack -> {
                _pcShippingUISt.value = PcShippingUIStateModel(isClearAllTextField = true)
                _pcShippingUISt.value = onPcShippingUIStateSuccess(navigateType = PcShippingNavigateType.GO_BACK)
            }
            is PcShippingAction.TypingShipmentLotTextField -> {
                _pcShippingUISt.value = _pcShippingUISt.value.copy(
                    shipmentLot = viewAction.text,
                    isClickedShipmentLot = false
                )
            }
            is PcShippingAction.ClickNextActionShipmentLot -> { callCheckShipmentLot() }
            is PcShippingAction.TypingTruckTextField -> {
                _pcShippingUISt.value = _pcShippingUISt.value.copy(
                    truck = viewAction.text,
                    isGetTruckSuccess = false
                )
            }
            is PcShippingAction.ClickNextActionTruck -> { checkTruckNumber()}
            is PcShippingAction.TypingCoilNumberTextField -> {
                _pcShippingUISt.value = _pcShippingUISt.value.copy(
                    coilNumber = viewAction.text,
                    isClickedCoilNo = false
                )
            }
            is PcShippingAction.ClickNextActionCoilNumber -> { checkCoilNo()}
            is PcShippingAction.TypingWidthTextField -> {

                var widthType:String = viewAction.text

                if (!widthType.isDigitsOnly()) {
                    println("test")
                    _pcShippingUISt.value = onPcShippingUIStateError(
                        pcShippingErrorType = PcShippingErrorType.ERROR_STRING_TYPE,
                    )
                    return
                }

                if (widthType.isEmpty()) widthType = "0.0"

                _pcShippingUISt.value = _pcShippingUISt.value.copy(
                    width = widthType.toDouble()
                )
            }

            is PcShippingAction.ClickNextActionWidth -> { checkWidth() }

            is PcShippingAction.SetInitFlagClearAllTextField -> {
                _pcShippingUISt.value = PcShippingUIStateModel(isClearAllTextField = false)
            }
            is PcShippingAction.SetInitFlagGetShipmentLotSuccess -> {
                _pcShippingUISt.value = onPcShippingUIStateSuccess().copy(
                    isGetShipmentLotSuccess = false
                )
            }
            is PcShippingAction.SetInitFlagGetTruckSuccess -> {
                _pcShippingUISt.value = onPcShippingUIStateSuccess().copy(
                    isGetTruckSuccess = false
                )
            }
            is PcShippingAction.SetInitFlagGetTruckFail -> {
                _pcShippingUISt.value = onPcShippingUIStateSuccess().copy(
                    isGetTruckFail = false
                )
            }
            is PcShippingAction.SetInitGetCoilResp -> {
                _pcShippingUISt.value = onPcShippingUIStateSuccess().copy(
                    isGetCoilResp = false
                )
            }
            is PcShippingAction.SetInitGetCoilRespWithNeedWidth -> {
                _pcShippingUISt.value = onPcShippingUIStateSuccess().copy(
                    isGetCoilRespWithNeedWidth = false
                )
            }
            is PcShippingAction.SetInitGetWidthSuccess -> {
                _pcShippingUISt.value = onPcShippingUIStateSuccess().copy(
                    isGetWidthSuccess = false
                )
            }
            is PcShippingAction.SetInitGetWidthFail -> {
                _pcShippingUISt.value = onPcShippingUIStateSuccess().copy(
                    isGetWidthFail = false
                )
            }
            is PcShippingAction.CheckGetDataBeforeClearAll -> {
                val value = _pcShippingUISt.value
                val (dockCount, countDockMatch) = value.resListCoil.partition {
                    it.getCoilListResponse.dock == value.dock
                }.let { (matchingDock, _) ->
                    matchingDock.size to matchingDock.count { it.isMatched}
                }

                if (dockCount != countDockMatch && dockCount != 0) {
                    println("$dockCount $countDockMatch")
                    value.isNextCoil = false
//                    value.isNextWidth = true
//                    actionFromDialog(PcShippingDialogAction.ClickContinueClearAllDialogButton)
                }
                if (value.isNextShipmentLot.not()
                    || value.isNextTruck.not()
                    || value.isNextCoil.not()
                    || value.isNextWidth.not()){
                    println(value.isNextShipmentLot)
                    println(value.isNextTruck)
                    println(value.isNextCoil)
                    println(value.isNextWidth)
                    actionFromDialog(PcShippingDialogAction.ClickContinueClearAllDialogButton)
                }
            }

            is PcShippingAction.InitNavigateData -> {initNavigateData()}
        }
    }

    fun actionFromDialog(dialogAction: PcShippingDialogAction){
        when(dialogAction){
            is PcShippingDialogAction.ClickLeftPcShippingDialogButton -> {
                when(_pcShippingUISt.value.errorBody?.pcShippingErrorType){
                    PcShippingErrorType.ERROR_FROM_API_SHIPMENT_LOT,
                    PcShippingErrorType.EMPTY_SHIPMENT_LOT,
                    PcShippingErrorType.DIGIT_SHIPMENT_LOT->{
                        _pcShippingUISt.value = PcShippingUIStateModel(
                            isClearAllTextField = true
                        )
                    }
                    PcShippingErrorType.TRUCK_NOT_MATCHED,
                    PcShippingErrorType.EMPTY_TRUCK ->{
                        _pcShippingUISt.value = onPcShippingUIStateSuccess(
                            navigateType = PcShippingNavigateType.START_COUNTDOWN_TIMER
                        ).copy(
                            coilNumber = "",
                            truck = "",
                            isGetTruckFail = true,
                            countDownTime = COUNT_DOWN_TRUCK
                        )
                        println(_pcShippingUISt.value.isNextTruck)
                    }
                    PcShippingErrorType.EMPTY_COIL_NUMBER,
                    PcShippingErrorType.DUPLICATE_COIL_NUMBER,
                    PcShippingErrorType.DIGIT_COIL,
                    PcShippingErrorType.ERROR_FROM_API_COIL_NO-> {

                        _pcShippingUISt.value = onPcShippingUIStateSuccess(
                            navigateType = PcShippingNavigateType.START_COUNTDOWN_TIMER
                        ).copy(
                            coilNumber = "",
                            isGetCoilResp = true,
                            countDownTime = COUNT_DOWN_COIL
                        )
                    }
                    PcShippingErrorType.EMPTY_WIDTH,
                    PcShippingErrorType.ERROR_RANGE_WIDTH,
                    PcShippingErrorType.ERROR_STRING_TYPE-> {
                        _pcShippingUISt.value = onPcShippingUIStateSuccess(
                            navigateType = PcShippingNavigateType.START_COUNTDOWN_TIMER
                        ).copy(
                            width = 0.0,
                            isGetWidthFail = true,
                            countDownTime = COUNT_DOWN_WIDTH
                        )
                    }

                    else -> action(PcShippingAction.InitNavigateData)
                }
            }
            is PcShippingDialogAction.ClickContinueClearAllDialogButton -> {
                _pcShippingUISt.value = PcShippingUIStateModel(
                    isClearAllTextField = true,
                )
                _pcShippingUISt.value = onPcShippingUIStateSuccess(
                    navigateType = PcShippingNavigateType.STOP_COUNTDOWN_TIMER
                )

                println("After ClearAll")
                println("Shipment ${_pcShippingUISt.value.isNextShipmentLot}")
                println("Truck ${_pcShippingUISt.value.isNextTruck}")
                println("Coil ${_pcShippingUISt.value.isNextCoil}")
                println("Width ${_pcShippingUISt.value.isNextWidth}")
            }
            is PcShippingDialogAction.ClickSendButton -> {}
            is PcShippingDialogAction.ClickClearAllButton -> {
                _pcShippingUISt.value = onPcShippingUIStateSuccess(
                    navigateType = PcShippingNavigateType.DISPLAY_CLEAR_ALL_DIALOG_WARNING,
                    successMsg = "ต้องการลบข้อมูลทั้งหมดหรือไม่"
                )
            }
        }
    }

    private fun callCheckShipmentLot() {

        if (_pcShippingUISt.value.shipmentLot.isEmpty()){
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.EMPTY_SHIPMENT_LOT
            )
            return
        }
        if (_pcShippingUISt.value.shipmentLot.length < MAX_LENGTH_SHIPMENT_LOT){
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.DIGIT_SHIPMENT_LOT
            )
            return
        }

        viewModelScope.launch {
            pcShippingUC.getCheckShipmentLot(_pcShippingUISt.value.shipmentLot).collect{
                when(it){
                    is NetworkResult.Success -> {
                        var truck = ""
                        var isGetTruckValSuccess = false
                        var navigateType = PcShippingNavigateType.NONE

                        if (_pcShippingUISt.value.shipmentLot.first() == 'B'||
                            _pcShippingUISt.value.shipmentLot.first() == 'Z') {
                            truck = "X"
                            isGetTruckValSuccess = true
                            navigateType = PcShippingNavigateType.START_COUNTDOWN_TIMER

                    }
                        _pcShippingUISt.value = onPcShippingUIStateSuccess(
                            navigateType
                        ).copy(
                            isGetShipmentLotSuccess = true,
                            resListCoil = it.data.mapToCoilDetail(),
                            countDownTime = COUNT_DOWN_TRUCK,
                            isNextShipmentLot = true,
                            truck = truck,
                            isGetTruckSuccess = isGetTruckValSuccess,
                            isNextTruck = isGetTruckValSuccess
                        )
                        println(pcShippingUISt.value.truck)
                    }
                    is NetworkResult.Loading -> {onPcShippingUIStateLoading()}
                    is NetworkResult.Error -> {
                        _pcShippingUISt.value =onPcShippingUIStateError(
                            pcShippingErrorType = PcShippingErrorType.ERROR_FROM_API_SHIPMENT_LOT,
                            errorMsg = it.errorMessage
                        )
                    }
                }
            }
        }
    }


    private fun checkTruckNumber(){

        if (_pcShippingUISt.value.shipmentLot.isEmpty()){
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.EMPTY_SHIPMENT_LOT
            )
            return
        }
        if (_pcShippingUISt.value.shipmentLot.length < MAX_LENGTH_SHIPMENT_LOT){
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.DIGIT_SHIPMENT_LOT
            )
            return
        }
        if (_pcShippingUISt.value.truck.isEmpty()) {
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.EMPTY_TRUCK
            )
            return
        }
        if (_pcShippingUISt.value.isNextShipmentLot.not()) {
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.NEXT_ACTION_SHIPMENT
            )
        }
        if (_pcShippingUISt.value.truck.first() == 'P' ||
            _pcShippingUISt.value.truck.first() == 'X') {
            _pcShippingUISt.value = onPcShippingUIStateSuccess(
                navigateType = PcShippingNavigateType.START_COUNTDOWN_TIMER
            ).copy(
                isGetTruckSuccess = true,
                isNextTruck = true,
                countDownTime = COUNT_DOWN_TRUCK
            )
            return
        }
        if (_pcShippingUISt.value.resListCoil.any{
            coilDetail -> coilDetail.getCoilListResponse.truckNumber == _pcShippingUISt.value.truck
            }){
            _pcShippingUISt.value = onPcShippingUIStateSuccess(
                navigateType = PcShippingNavigateType.START_COUNTDOWN_TIMER
            ).copy(
                isGetTruckSuccess = true,
                isNextTruck = true,
                countDownTime = COUNT_DOWN_TRUCK
            )
            return
        }
        _pcShippingUISt.value = onPcShippingUIStateError(
            pcShippingErrorType = PcShippingErrorType.TRUCK_NOT_MATCHED
        )

    }

    private fun checkCoilNo(){

        if (_pcShippingUISt.value.shipmentLot.isEmpty()){
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.EMPTY_SHIPMENT_LOT
            )
            return
        }
        if (_pcShippingUISt.value.shipmentLot.length < MAX_LENGTH_SHIPMENT_LOT){
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.DIGIT_SHIPMENT_LOT
            )
            return
        }

        if (_pcShippingUISt.value.truck.isEmpty()) {
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.EMPTY_TRUCK
            )
            return
        }

        if (_pcShippingUISt.value.coilNumber.isEmpty()){
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.EMPTY_COIL_NUMBER
            )
            return
        }
        if (_pcShippingUISt.value.coilNumber.length < MAX_LENGTH_COIL_NO-2){
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.DIGIT_COIL
            )
            return
        }
        if (_pcShippingUISt.value.resListCoil.any{
            coilDetail -> coilDetail.getCoilListResponse.coilNumber == _pcShippingUISt.value.coilNumber.substring(0,8)
                    && coilDetail.isMatched
            }){
                _pcShippingUISt.value = onPcShippingUIStateError(
                    pcShippingErrorType = PcShippingErrorType.DUPLICATE_COIL_NUMBER
                )
            return
        }
        viewModelScope.launch {
            pcShippingUC.getCheckCoilNo(_pcShippingUISt.value.shipmentLot,_pcShippingUISt.value.coilNumber).collect{
                when(it){
                    is NetworkResult.Success ->{
                        println(it.data)
                        var coilNo = _pcShippingUISt.value.coilNumber
                        var needWidth = true
                        var countDown = COUNT_DOWN_WIDTH

                        if (it.data?.needWidth.equals("NO") ){
                            coilNo =""
                            needWidth = false
                            countDown = COUNT_DOWN_COIL
//                            _pcShippingUISt.value = onPcShippingUIStateSuccess().copy(
//                                coilNumber = "",
//                                isGetCoilResp = true
//                            )
                            _pcShippingUISt.value.resListCoil.setMatchedItemFrom(_pcShippingUISt.value.coilNumber.substring(0,8))
                            action(PcShippingAction.InitNavigateData)
                        }
                       _pcShippingUISt.value = _pcShippingUISt.value.copy(

                       )
//                        else{
//                            _pcShippingUISt.value = onPcShippingUIStateSuccess().copy(
//                                isGetCoilRespWithNeedWidth = true,
//                                isEnabledWidth = true
//                            )
//                        }
                        _pcShippingUISt.value = onPcShippingUIStateSuccess(
                            navigateType = PcShippingNavigateType.START_COUNTDOWN_TIMER
                        ).copy(
                            coilNumber = coilNo,
                            isGetCoilResp = needWidth.not(),
                            isGetCoilRespWithNeedWidth = needWidth,
                            isEnabledWidth = needWidth,
                            countDownTime = countDown,
                            isNextCoil = true,
                            isNextWidth = needWidth.not(),
                            dock = _pcShippingUISt.value.resListCoil
                                .getMatchedItemWithCoilNumber(_pcShippingUISt.value.coilNumber)
                                ?.getCoilListResponse?.dock ?: ""
                        )

                    }
                    is NetworkResult.Loading -> {onPcShippingUIStateLoading()}
                    is NetworkResult.Error -> {
                        _pcShippingUISt.value = onPcShippingUIStateError(
                            pcShippingErrorType = PcShippingErrorType.ERROR_FROM_API_COIL_NO,
                            errorMsg = it.errorMessage
                        )
                    }
                }
            }
        }


    }

    private fun checkWidth(){
        println("Start check width")
        val widthResp = _pcShippingUISt.value.resListCoil
            .find { it.getCoilListResponse.coilNumber == _pcShippingUISt.value.coilNumber.substring(0,8) }
            ?.getCoilListResponse?.width

        println("Get width : $widthResp")

        val widthDift = widthResp?.minus(_pcShippingUISt.value.width)

        println("Dift = $widthDift")

        onPcShippingUIStateLoading()
        if(  widthDift != null && (widthDift <= -15 || widthDift >= 15) ){
            val lineText1 = "ค่า Width(${formatDouble(_pcShippingUISt.value.width)}) ไม่อยู่ใน Range"
            val lineText2 = "(${widthResp-15} - ${widthResp+15})"
            val lengthLine1 = lineText1.length/2
            println()
            _pcShippingUISt.value = onPcShippingUIStateError(
                pcShippingErrorType = PcShippingErrorType.ERROR_RANGE_WIDTH,
                errorMsg = lineText1 +"\n"+
                        " ".repeat(lengthLine1)+lineText2
            )
        }
        else{
            _pcShippingUISt.value.resListCoil.setMatchedItemFrom(_pcShippingUISt.value.coilNumber.substring(0,8))
            _pcShippingUISt.value = onPcShippingUIStateSuccess(
                navigateType = PcShippingNavigateType.START_COUNTDOWN_TIMER
            ).copy(
                coilNumber = "",
                width = 0.0,
                isNextWidth = true,
                isGetWidthSuccess = true,
                isEnabledWidth = false,
                countDownTime = COUNT_DOWN_COIL
            )
            println(_pcShippingUISt.value.coilNumber)
            println("${_pcShippingUISt.value.width}")
            action(PcShippingAction.InitNavigateData)
        }
    }

    fun initNavigateData() {
        _pcShippingUISt.value = onPcShippingUIStateSuccess()
    }

    private fun onPcShippingUIStateSuccess(
        navigateType: PcShippingNavigateType =PcShippingNavigateType.NONE,
        successMsg : String? = null
    ):PcShippingUIStateModel = _pcShippingUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        isError = false,
        navigateType = navigateType,
        message = successMsg,
        errorBody = null
    )

    private fun onPcShippingUIStateError(
        pcShippingErrorType: PcShippingErrorType = PcShippingErrorType.ERROR_FROM_API,
        errorMsg : String? = null
    ): PcShippingUIStateModel = _pcShippingUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        isError = true,
        message = null,
        errorBody = PcShippingErrorModel(
            pcShippingErrorType =pcShippingErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onPcShippingUIStateLoading(isLoading: Boolean = true){
        _pcShippingUISt.value = _pcShippingUISt.value.copy(isLoading = isLoading)
    }


}