package com.nssus.ihandy.ui.production.cvloading.viewmodel

import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.data.extension.isValidFirstChar
import com.nssus.ihandy.data.util.FIleUtil.uploadFileUsingFTP
import com.nssus.ihandy.data.util.FIleUtil.writeOrAppendTextToFile
import com.nssus.ihandy.model.common.shipbypro.ShipByProAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProDialogAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProErrorType
import com.nssus.ihandy.model.common.shipbypro.ShipByProUIStateModel
import com.nssus.ihandy.model.common.yardentry.YardEntryDialogAction
import com.nssus.ihandy.model.common.yardentry.YardEntryNavigateType
import com.nssus.ihandy.model.common.yardentry.YardEntryUIStateModel
import com.nssus.ihandy.model.production.cvloading.CVLoadingAction
import com.nssus.ihandy.model.production.cvloading.CVLoadingDialogAction
import com.nssus.ihandy.model.production.cvloading.CVLoadingErrorModel
import com.nssus.ihandy.model.production.cvloading.CVLoadingErrorType
import com.nssus.ihandy.model.production.cvloading.CVLoadingNavigateType
import com.nssus.ihandy.model.production.cvloading.CVLoadingUIStateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CVLoadingViewModel(


) : ViewModel() {
    private val _cvLoadingUISt = mutableStateOf(CVLoadingUIStateModel())
    val cvLoadingUISt: State<CVLoadingUIStateModel> = _cvLoadingUISt

    fun initData(){
        if (_cvLoadingUISt.value.isAlreadyInitialFeature) {return}

        _cvLoadingUISt.value = CVLoadingUIStateModel(isAlreadyInitialFeature = true)
    }

    fun action(viewAction: CVLoadingAction) {
        when (viewAction) {
            //Show warning dialog before delete
            is CVLoadingAction.ClickClearAll ->{
                println("[View model : Called clear all dialog from viewmodel!]")
                _cvLoadingUISt.value = onCVLoadingUIStateSuccess(
                    navigateType = CVLoadingNavigateType.DISPLAY_CLEAR_ALL_DIALOG,
                    successMsg = "ต้องการลบข้อมูลหรือไม่?"
                )
            }
//            is CVLoadingAction.ClickContinueClearAll -> {
//                println("[View model : Click Ok clear all action viewmodel]")
//                _cvLoadingUISt.value = CVLoadingUIStateModel(
//                    isClearAllTextFieldValue = true
//                )
//            }
            is CVLoadingAction.GoBack -> {
                _cvLoadingUISt.value = CVLoadingUIStateModel(isClearAllTextFieldValue = true)
                _cvLoadingUISt.value = onCVLoadingUIStateSuccess(
                    navigateType = CVLoadingNavigateType.GO_BACK
                )
            }
            //Input keyboard (Coil text field)
            is CVLoadingAction.TypingCoilNoTextField -> { //
                _cvLoadingUISt.value = _cvLoadingUISt.value.copy(
                    coilNo = viewAction.text,
                    isClickNextActionCoilNo = false
                )
            }
            //Check coil condition
            is CVLoadingAction.ClickNextActionCoilNoTextField -> { callCheckCoilCondition() }

            //Input keyboard (Line skid code text field)
            is CVLoadingAction.TypingLCSTextField -> { //
                _cvLoadingUISt.value = _cvLoadingUISt.value.copy(
                    lcsNo = viewAction.text,
                    isClickNextActionCoilNo = false
                )
            }

            //Input keyboard (supplier text field)
            is CVLoadingAction.TypingSupplierNoTextField -> { //
                _cvLoadingUISt.value = _cvLoadingUISt.value.copy(
                    supplierNo = viewAction.text,
                    isClickNextActionCoilNo = false
                )
            }

            //Set Init
            is CVLoadingAction.SetInitFlagClearAllTextField ->{
                _cvLoadingUISt.value = onCVLoadingUIStateSuccess().copy(
                    isClearAllTextFieldValue = false
                )
            }
            is CVLoadingAction.SetInitFlagGetCoilNumberResp ->{
                _cvLoadingUISt.value = onCVLoadingUIStateSuccess().copy(
                    isGetCoilNumberResp = false,
                )
            }
            is CVLoadingAction.SetInitFlagGetLineSkidResp -> {
                _cvLoadingUISt.value = onCVLoadingUIStateSuccess().copy(
                    isGetLineSkidResp = false
                )
            }
            is CVLoadingAction.SetInitFlagGetSupplierNumberResp ->{
                _cvLoadingUISt.value = onCVLoadingUIStateSuccess().copy(
                    isGetSupplierNumberResp = false,
                )
            }

            //Next action (condition check the logic)
            is CVLoadingAction.ClickNextActionCoilNoTextField -> { callCheckCoilCondition() }

            //is CVLoadingAction.ClickNextActionLCSTextField -> { callCheckShip() }

            //is CVLoadingAction.ClickNextActionSupplierNoTextField -> { callCheckCoil() }

            is CVLoadingAction.ClickSendButton -> {
                checkConditionCoilNo()
                println("SSSS: ${_cvLoadingUISt.value.coilNo} ${_cvLoadingUISt.value.lcsNo} ${_cvLoadingUISt.value.supplierNo}")
            }


            is CVLoadingAction.ClickClearButton -> {
                _cvLoadingUISt.value = CVLoadingUIStateModel()
                _cvLoadingUISt.value = onCVLoadingUIStateSuccess().copy(
                    coilNo = "",
                    lcsNo = "",
                    supplierNo = ""
                )
            }
        }
    }

    fun actionFromDialog(viewAction: CVLoadingDialogAction){
        when(viewAction) {
            //Click continue to clear all
            is CVLoadingDialogAction.ClickContinueClearAll -> {
                println("[View model : Click Ok clear all action viewmodel]")
                _cvLoadingUISt.value = CVLoadingUIStateModel(
                    isClearAllTextFieldValue = true
                )
            }
        }
    }

    fun initNavigateData() {
        _cvLoadingUISt.value = onCVLoadingUIStateSuccess()
    }

    private fun checkConditionCoilNo() {
        if (_cvLoadingUISt.value.coilNo.isValidFirstChar()) {
            _cvLoadingUISt.value = onCVLoadingUIStateError(
                cvLoadingErrorType = CVLoadingErrorType.ERROR_E003,
            )
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            writeOrAppendTextToFile(
                fileName = "coil_detail_1.txt", // "coil_detail_test.txt",
                text = "", // Whatever input that you want to write the file
                onWriteFileSuccess = { initNavigateData() },
                onWriteFileError = { _cvLoadingUISt.value = onCVLoadingUIStateError(errorMsg = it) }
            )

            val filePath =
                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/coil_detail_1.txt"
            val server = "203.151.30.95" // Use the correct FTP server IP address
            val username = "ihandy"
            val password = "password@1"
            val remoteDirectory = "/remote/path"  // Remote directory on the FTP server

            uploadFileUsingFTP(
                filePath = filePath,
                server = server,
                username = username,
                password = password,
                remoteDirectory = remoteDirectory,
                onSuccess = {
                    println("File uploaded successfully!")
                },
                onError = { error ->
                    println("Error uploading file: $error")
                }
            )
        }
    }

    private fun callCheckCoilCondition() {
        //Check if the first digit is a minus sign(-)
        if (_cvLoadingUISt.value.coilNo.startsWith("-") ){
            _cvLoadingUISt.value.coilNo.substring(1)
        }
        //Check for specific HC work show value ("I" or "J")
        if (_cvLoadingUISt.value.coilNo.startsWith("-") ){
            _cvLoadingUISt.value.coilNo.substring(1)
        }
        if (_cvLoadingUISt.value.coilNo.startsWith("-") ){
            _cvLoadingUISt.value.coilNo.substring(1)
        }

        return
    }



    private fun onCVLoadingUIStateSuccess(
        navigateType: CVLoadingNavigateType = CVLoadingNavigateType.NONE,
        successMsg: String? = null
    ): CVLoadingUIStateModel = _cvLoadingUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onCVLoadingUIStateError(
        cvLoadingErrorType: CVLoadingErrorType = CVLoadingErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): CVLoadingUIStateModel = _cvLoadingUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = CVLoadingNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = CVLoadingErrorModel(
            cvLoadingErrorType = cvLoadingErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onCVLoadingUIStateLoading(isLoading: Boolean = true) {
        _cvLoadingUISt.value = _cvLoadingUISt.value.copy(isLoading = isLoading)
    }
}