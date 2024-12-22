package com.nssus.ihandy.ui.shipping.pcshipping

import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.data.util.AppUtil.getCountDownTimer
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingAction
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingDialogAction
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingNavigateType
import com.nssus.ihandy.ui.basecomposable.BaseMsgWithButtonDialog
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.basecomposable.WarningDialog
import com.nssus.ihandy.ui.shipping.pcshipping.viewmodel.PcShippingViewModel
import com.nssus.ihandy.ui.theme.Black50
import com.nssus.ihandy.ui.theme.WarningRed

@Composable
fun PcShippingRoute(
    navController: NavController,
    pcShippingVM: PcShippingViewModel
) {
    val uiPcShipSt by pcShippingVM.pcShippingUISt

    var countDownTimer: CountDownTimer? by remember {  mutableStateOf(null) }

    LaunchedEffect(Unit) {
        pcShippingVM.initData()        
    }

    when {
        uiPcShipSt.isLoading -> CustomLoading()
        uiPcShipSt.isSuccess -> {
            when(uiPcShipSt.navigateType){
                PcShippingNavigateType.GO_BACK ->{
                    navController.popBackStack()
                    pcShippingVM.action(PcShippingAction.InitNavigateData)
                }
                PcShippingNavigateType.DISPLAY_NEXT_ACTION_DIALOG_WARNING -> {
                    WarningDialog(message = uiPcShipSt.message?: "-",
                        twoButton = false,
                        onLeftDialogButtonClick = { pcShippingVM.action(PcShippingAction.InitNavigateData) })
                }
                PcShippingNavigateType.DISPLAY_CLEAR_ALL_DIALOG_WARNING -> {
                    WarningDialog(
                        message = uiPcShipSt.message?: "",
                        onLeftDialogButtonClick = {pcShippingVM.actionFromDialog(PcShippingDialogAction.ClickContinueClearAllDialogButton)},
                        onRightDialogButtonClick = {pcShippingVM.action(PcShippingAction.InitNavigateData)}
                    )
                }
                PcShippingNavigateType.START_COUNTDOWN_TIMER -> {
                    pcShippingVM.action(PcShippingAction.InitNavigateData)
                    countDownTimer?.cancel()
                    println("count ${uiPcShipSt.countDownTime}")
                    countDownTimer = getCountDownTimer(
                        countDownTimeInSec = uiPcShipSt.countDownTime,
                        onCountDownFinish = {
                            countDownTimer?.cancel()
                            println("cancel count")
                            pcShippingVM.action(PcShippingAction.CheckGetDataBeforeClearAll)
                        }
                    ).apply { start() }
                }
                PcShippingNavigateType.STOP_COUNTDOWN_TIMER -> {
                    countDownTimer?.cancel()
                    println("STOP after clear all")
                    pcShippingVM.action(PcShippingAction.InitNavigateData)
                }
                PcShippingNavigateType.NONE -> {}
            }
        }
        uiPcShipSt.isError -> {
            BaseMsgWithButtonDialog(
                message = (if (uiPcShipSt.errorBody?.errorMsg.isNullOrEmpty()) stringResource(id = uiPcShipSt.errorBody?.pcShippingErrorType?.errorMsgId ?: R.string.dash_string)
                else uiPcShipSt.errorBody?.errorMsg) ?: "-",
                icon = R.drawable.ic_dialog_red_cross,
                messageColor = Black50,
                leftButtonTextColor = Color.White,
                leftButtonColor = WarningRed,
                leftButtonBorderColor = WarningRed,
                onLeftButtonClick = { pcShippingVM.actionFromDialog(PcShippingDialogAction.ClickLeftPcShippingDialogButton) })
        }
    }
    PcShippingScreen(
        uiPcShippingSt = uiPcShipSt,
        onAction = pcShippingVM::action,
        onDialogAction = pcShippingVM::actionFromDialog
    )


}