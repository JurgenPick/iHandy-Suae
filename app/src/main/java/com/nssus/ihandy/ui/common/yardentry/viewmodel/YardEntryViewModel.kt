package com.nssus.ihandy.ui.common.yardentry.viewmodel

import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.data.extension.formatYardDigit
import com.nssus.ihandy.data.usecase.common.yardentry.YardEntryUseCase
import com.nssus.ihandy.model.common.yardentry.YardEntryAction
import com.nssus.ihandy.model.common.yardentry.YardEntryDialogAction
import com.nssus.ihandy.model.common.yardentry.YardEntryErrorModel
import com.nssus.ihandy.model.common.yardentry.YardEntryErrorType
import com.nssus.ihandy.model.common.yardentry.YardEntryNavigateType
import com.nssus.ihandy.model.common.yardentry.YardEntryUIStateModel
import com.nssus.ihandy.model.common.yardentry.YardInformation
import com.nssus.ihandy.model.common.yardentry.YardRequest
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.launch

class YardEntryViewModel(
    private val yardEntryUC: YardEntryUseCase
) : ViewModel() {
    private val _yardEntryUISt = mutableStateOf(YardEntryUIStateModel())
    val yardEntryUISt: State<YardEntryUIStateModel> = _yardEntryUISt

    fun initData(){
        _yardEntryUISt.value = YardEntryUIStateModel()
    }

    fun action(viewAction: YardEntryAction) {
        when (viewAction) {
            is YardEntryAction.GoBack -> {
                _yardEntryUISt.value = YardEntryUIStateModel(isClearAllTextFieldValue = true)
                _yardEntryUISt.value = onYardEntryUIStateSuccess(
                    navigateType = YardEntryNavigateType.GO_BACK
                )
            }

            //Coil text field
            is YardEntryAction.TypingCoilNoTextField -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    coilNo = viewAction.text,
                    isClickNextActionCoilNo = false
                )
            }

            is YardEntryAction.ClickNextActionCoilTextField -> { checkCoilNo() }

            //Supplier text field
            is YardEntryAction.TypingSupplierNoTextField -> { //

                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    supplierNo = viewAction.text,
                    isClickNextActionCoilNo = false
                )
            }

            is YardEntryAction.ClickNextActionSupplierNoTextField -> { checkSupplierNo() }

            //Yard text field
            is YardEntryAction.TypingYYRRCCTTextField -> { //
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    yyrrcct = viewAction.text
                )
            }

            is YardEntryAction.ClickNextActionYYRRCCTTextField -> { checkYard() }

            //setFlag
            is YardEntryAction.InitNavigateData -> { initNavigateData() }

            is YardEntryAction.SetInitFlagGetCoilSuccess -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetCoilSuccess = false
                )
            }

            is YardEntryAction.SetInitFlagGetCoilWithSupplierSuccess -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetCoilWithSupplierSuccess = false
                )
            }

            is YardEntryAction.SetInitFlagGetCoilFail -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetCoilFail = false
                )
            }

            is YardEntryAction.SetInitFlagGetSupplierSuccess -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetSupplierNoSuccess = false
                )
            }

            is YardEntryAction.SetInitFlagGetSupplierFail -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetSupplierNoFail = false
                )
            }

            is YardEntryAction.SetInitFlagGetYYRRCCTSuccess -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetYYRRCCTSuccess = false
                )
            }

            is YardEntryAction.SetInitFlagGetYYRRCCTFail -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetYYRRCCTFail = false
                )
            }

            is YardEntryAction.SetInitFlagClearAllTextField -> {
                _yardEntryUISt.value = YardEntryUIStateModel(isClearAllTextFieldValue = false)
            }

        }
    }

    fun actionFromDialog(viewAction: YardEntryDialogAction){
        when(viewAction){
            is YardEntryDialogAction.ClickClearAll -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess(
                    navigateType = YardEntryNavigateType.DISPLAY_CLEAR_ALL_DIALOG,
                    successMsg = "ต้องการลบข้อมูลหรือไม่ได้"
                )
            }
            is YardEntryDialogAction.ClickContinueClearAll,
            YardEntryDialogAction.ClickConfirmSendMQ->{
                _yardEntryUISt.value = YardEntryUIStateModel(
                    isClearAllTextFieldValue = true
                )
            }
            is YardEntryDialogAction.ClickConfirmReserved -> sendMQ()
            is YardEntryDialogAction.ClickCancelReserved -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetYYRRCCTFail = true
                )
            }

            is YardEntryDialogAction.ClickLeftYardEntryDialogButton -> {
                when(_yardEntryUISt.value.errorBody?.yardEntryErrorType){
                    YardEntryErrorType.ERROR_EMPTY_COIL,
                    YardEntryErrorType.ERROR_COIL_API,
                    YardEntryErrorType.ERROR_SUPPLIER_API,
                    YardEntryErrorType.ERROR_SEND_MQ->{
                        _yardEntryUISt.value = YardEntryUIStateModel(
                            isClearAllTextFieldValue = true
                        )
                    }
                    YardEntryErrorType.ERROR_EMPTY_SUPPLIER -> {
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            supplierNo = "",
                            isGetSupplierNoFail = true
                        )
                    }
                    YardEntryErrorType.ERROR_MISS_NEXT_CLICK -> {
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            atErrorInYardTextBox = false,
                            yyrrcct = "",
                            isGetYYRRCCTFail = true
                        )
                        if (_yardEntryUISt.value.needSupplierNo) checkSupplierNo()
                        else checkCoilNo()
                    }
                    YardEntryErrorType.ERROR_FORMAT_YARD,
                    YardEntryErrorType.ERROR_HC_TO_CDCM,
                    YardEntryErrorType.ERROR_EMPTY_YARD,
                    YardEntryErrorType.ERROR_YARD_API-> {
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            atErrorInYardTextBox = false,
                            yyrrcct = "",
                            isGetYYRRCCTFail = true
                        )
                    }
                    else -> action(YardEntryAction.InitNavigateData)
                }
            }
        }
    }

    private fun checkCoilNo(){


        if (checkCoilNoIsEmpty()) return

        if (_yardEntryUISt.value.coilNo.first() == 'S') {
            _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                needSupplierNo = true,
                isGetCoilWithSupplierSuccess = true
            )
            return
        }
        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
            needSupplierNo = false
        )
        viewModelScope.launch {
            yardEntryUC.getCheckCoil(_yardEntryUISt.value.coilNo).collect{
                when(it){
                    is NetworkResult.Success ->{
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            isClickNextActionCoilNo = true,
                            isGetCoilSuccess = true,
                            yardCurrent = it.data?.yard?.yardCode+" "
                                    +it.data?.yard?.yardRow+it.data?.yard?.yardColumn+it.data?.yard?.yardTier
                        )
                    }
                    is NetworkResult.Loading ->{
                        onYardEntryUIStateLoading()
                    }
                    is NetworkResult.Error ->{
                        _yardEntryUISt.value = onYardEntryUIStateError(
                            yardEntryErrorType = YardEntryErrorType.ERROR_COIL_API,
                            errorMsg = it.errorMessage
                        )
                    }
                }
            }
        }

    }
    private fun checkSupplierNo(){
        if ( checkCoilNoIsEmpty() || checkSupplierNoIsEmpty() ) return
        val supplierNo = _yardEntryUISt.value.supplierNo.padEnd(14,' ')
        viewModelScope.launch {
            yardEntryUC.getCheckCoilWithSupplier(_yardEntryUISt.value.coilNo,supplierNo).collect{
                when(it){
                    is NetworkResult.Success ->{
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            isClickNextActionCoilNo = true,
                            isGetSupplierNoSuccess = true,
                            yardCurrent = it.data?.yard?.yardCode+" "
                                    +it.data?.yard?.yardRow+it.data?.yard?.yardColumn+it.data?.yard?.yardTier
                        )
                    }
                    is NetworkResult.Loading -> { onYardEntryUIStateLoading() }
                    is NetworkResult.Error -> {
                        _yardEntryUISt.value = onYardEntryUIStateError(
                            yardEntryErrorType = YardEntryErrorType.ERROR_SUPPLIER_API,
                            errorMsg = it.errorMessage
                        )

                    }
                }
            }
        }

    }
    private fun checkYard(){
        if (checkCoilNoIsEmpty()
            || (_yardEntryUISt.value.needSupplierNo&&checkSupplierNoIsEmpty())
            || checkYardIsEmpty()
            ) return

        if (!_yardEntryUISt.value.isClickNextActionCoilNo){
            val lineText1 = "คุณไม่ได้ Confirm Coil No \"${_yardEntryUISt.value.coilNo}\""
            val lineText2 = "คุณต้องการ Confirm Coil No หรือไม่"
            _yardEntryUISt.value = onYardEntryUIStateError(
                yardEntryErrorType = YardEntryErrorType.ERROR_MISS_NEXT_CLICK,
                errorMsg = lineText1
                        +"\n "+lineText2

            ).copy(
                atErrorInYardTextBox = true
            )
            return
        }
        if (_yardEntryUISt.value.yyrrcct.length < 5){
            _yardEntryUISt.value = onYardEntryUIStateError(
                yardEntryErrorType = YardEntryErrorType.ERROR_FORMAT_YARD
            ).copy(
                atErrorInYardTextBox = true
            )
            return
        }
        if (_yardEntryUISt.value.yyrrcct.first() =='G'
            && _yardEntryUISt.value.yardCurrent.first() in 'A'..'F'){
            _yardEntryUISt.value = onYardEntryUIStateError(
                yardEntryErrorType = YardEntryErrorType.ERROR_HC_TO_CDCM
            ).copy(
                atErrorInYardTextBox = true
            )
            return
        }
        _yardEntryUISt.value.yyrrcct = _yardEntryUISt.value.yyrrcct.formatYardDigit()

        println(getYardRequest())
        viewModelScope.launch {
            yardEntryUC.getCheckYard(getYardRequest()).collect{
                when(it){
                    is NetworkResult.Success -> {
                        if (it.data?.reservedYard != null){
                            _yardEntryUISt.value = onYardEntryUIStateSuccess(
                                navigateType = YardEntryNavigateType.RESERVED_COIL_NO,
                                successMsg = "มี Coil อื่นใช้งานแล้ว \n"+"คุณต้องการใช้งาน Yard นี้ไหม"
                            ).copy(
                                atErrorInYardTextBox = false
                            )
                            return@collect
                        }
                        sendMQ()
                    }
                    is NetworkResult.Loading -> { onYardEntryUIStateLoading() }
                    is NetworkResult.Error -> {
                        _yardEntryUISt.value = onYardEntryUIStateError(
                            yardEntryErrorType = YardEntryErrorType.ERROR_YARD_API,
                            errorMsg = it.errorMessage
                        )
                    }
                }
            }
        }
    }

    private fun getYardRequest() : YardRequest = YardRequest(
        coilNo = _yardEntryUISt.value.coilNo,
        supplierNo = _yardEntryUISt.value.supplierNo,
        deviceId = Build.DEVICE,
        yard = YardInformation(
            yardCode = _yardEntryUISt.value.yyrrcct.take(2).trim(),
            yardRow = _yardEntryUISt.value.yyrrcct.drop(2).take(2),
            yardColumn = _yardEntryUISt.value.yyrrcct.drop(4).take(2),
            yardTier = _yardEntryUISt.value.yyrrcct.drop(6)
        )
    )

    private fun checkCoilNoIsEmpty() : Boolean  {
        if (_yardEntryUISt.value.coilNo.isEmpty()){
            _yardEntryUISt.value = onYardEntryUIStateError(
                yardEntryErrorType = YardEntryErrorType.ERROR_EMPTY_COIL
            )
            return true
        }
        return false
    }
    private fun checkSupplierNoIsEmpty() : Boolean  {
        if (_yardEntryUISt.value.supplierNo.isEmpty()){
            _yardEntryUISt.value = onYardEntryUIStateError(
                yardEntryErrorType = YardEntryErrorType.ERROR_EMPTY_SUPPLIER
            )
            return true
        }
        return false
    }
    private fun checkYardIsEmpty() :Boolean {
        if (_yardEntryUISt.value.yyrrcct.isEmpty()){
            _yardEntryUISt.value = onYardEntryUIStateError(
                yardEntryErrorType = YardEntryErrorType.ERROR_EMPTY_YARD
            )
            return true
        }
        return false
    }
    private fun sendMQ() {
        viewModelScope.launch {
            yardEntryUC.sendMQ(getYardRequest()).collect{
                when(it){
                    is NetworkResult.Success ->{
                        _yardEntryUISt.value = onYardEntryUIStateSuccess(
                            navigateType = YardEntryNavigateType.DISPLAY_SUCCESS,
                            successMsg = "Update Success"
                        )
                    }
                    is NetworkResult.Loading ->{ onYardEntryUIStateLoading() }
                    is NetworkResult.Error ->{
                        _yardEntryUISt.value = onYardEntryUIStateError(
                            yardEntryErrorType = YardEntryErrorType.ERROR_SEND_MQ,
                            errorMsg = it.errorMessage
                        )
                    }
                }
            }
        }
    }
    fun initNavigateData() {
        _yardEntryUISt.value = onYardEntryUIStateSuccess()
    }

    private fun onYardEntryUIStateSuccess(
        navigateType: YardEntryNavigateType = YardEntryNavigateType.NONE,
        successMsg: String? = null
    ): YardEntryUIStateModel = _yardEntryUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onYardEntryUIStateError(
        yardEntryErrorType: YardEntryErrorType = YardEntryErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): YardEntryUIStateModel = _yardEntryUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = YardEntryNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = YardEntryErrorModel(
            yardEntryErrorType = yardEntryErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onYardEntryUIStateLoading(isLoading: Boolean = true) {
        _yardEntryUISt.value = _yardEntryUISt.value.copy(isLoading = isLoading)
    }
}