package com.nssus.ihandy.ui.production.cvloading

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.model.common.yardentry.YardEntryAction
import com.nssus.ihandy.model.common.yardentry.YardEntryDialogAction
import com.nssus.ihandy.model.common.yardentry.YardEntryNavigateType
import com.nssus.ihandy.model.production.cvloading.CVLoadingAction
import com.nssus.ihandy.model.production.cvloading.CVLoadingDialogAction
import com.nssus.ihandy.model.production.cvloading.CVLoadingNavigateType
import com.nssus.ihandy.ui.basecomposable.BaseMsgWithButtonDialog
import com.nssus.ihandy.ui.basecomposable.CompleteDialog
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.basecomposable.WarningDialog
import com.nssus.ihandy.ui.production.cvloading.viewmodel.CVLoadingViewModel
import com.nssus.ihandy.ui.theme.Black50
import com.nssus.ihandy.ui.theme.WarningRed

@Composable
fun CVLoadingRoute(
    navController: NavController,
    cvLoadingVm: CVLoadingViewModel
) {
    val uiCVLoadingSt by cvLoadingVm.cvLoadingUISt

    LaunchedEffect(Unit) {
        cvLoadingVm.initData()
    }

    when {
        uiCVLoadingSt.isLoading -> {println("Route none")
            CustomLoading() }
        uiCVLoadingSt.isSuccess -> {
            println("[Route file : Success!]")
            when (uiCVLoadingSt.navigateType) {
                CVLoadingNavigateType.GO_BACK -> {
                    navController.popBackStack()
                    cvLoadingVm.action(CVLoadingAction.InitNavigateData)
                }
                CVLoadingNavigateType.DISPLAY_CLEAR_ALL_DIALOG -> {
                    println("[Route file : Clear all dialog]")
                    WarningDialog(
                        message = uiCVLoadingSt.successMsg ?: " ",
                        onLeftDialogButtonClick = { cvLoadingVm.action(
                            CVLoadingAction.ClickContinueClearAll) },
                        onRightDialogButtonClick = { cvLoadingVm.action(
                            CVLoadingAction.InitNavigateData)})
                }
                CVLoadingNavigateType.DISPLAY_DIALOG_SEND_MQ -> {
                WarningDialog(
                    message = uiCVLoadingSt.successMsg ?: " ",
                    onLeftDialogButtonClick = { cvLoadingVm.actionFromDialog(
                        CVLoadingDialogAction.ClickConfirmSendMQ) },
                    onRightDialogButtonClick = { cvLoadingVm.action(
                        CVLoadingAction.InitNavigateData) }
                )
            }
                CVLoadingNavigateType.DISPLAY_DIALOG_SUCCESS -> {
                    CompleteDialog(
                        message = uiCVLoadingSt.successMsg ?: "",
                        onDialogButtonClick = {cvLoadingVm.actionFromDialog(
                            CVLoadingDialogAction.ClickContinueDialogButton)}
                    )
                }
                CVLoadingNavigateType.DISPLAY_DIALOG_WARNING -> {
                    WarningDialog(
                        message = uiCVLoadingSt.successMsg ?: "",
                        onLeftDialogButtonClick = { cvLoadingVm.actionFromDialog(
                            CVLoadingDialogAction.ClickContinueDialogButton) },
                        onRightDialogButtonClick = { cvLoadingVm.action(
                            CVLoadingAction.InitNavigateData) }
                    )
                }
//                CVLoadingNavigateType.GO_TO_COIL_DETAIL_LS -> {
//                    navController.navigate(ShipByProScreen.CoilListShipByProScreen.route)
//                    cvLoadingVm.action(CVLoadingAction.InitNavigateData)
//                }
                else -> {
                    println("[Route file : IsSuccess Else condition!]")
                }
            }
        }
        uiCVLoadingSt.isError -> {
            BaseMsgWithButtonDialog(
                message = (if (uiCVLoadingSt.errorBody?.errorMsg.isNullOrEmpty()) stringResource(id = uiCVLoadingSt.errorBody?.cvLoadingErrorType?.errorMsgId ?: R.string.dash_string)
                else uiCVLoadingSt.errorBody?.errorMsg) ?: "-" ,
                icon = R.drawable.ic_dialog_red_cross,
                messageColor = Black50,
                leftButtonTextColor = Color.White,
                leftButtonColor = WarningRed,
                leftButtonBorderColor = WarningRed,
                isHasPairButton = !uiCVLoadingSt.isClickNextActionCoilNo && uiCVLoadingSt.atErrorInCVLoadingTextBox,
                onLeftButtonClick = { cvLoadingVm.actionFromDialog(CVLoadingDialogAction.ClickLeftCVLoadingDialogButton) },
                onRightButtonClick = {cvLoadingVm.action(CVLoadingAction.InitNavigateData)}
            )
        }

    }
    CVLoadingScreen(
        uiCVLoadingSt = uiCVLoadingSt,
        onAction = cvLoadingVm::action
        //onDialogAction = cvLoadingVm::actionFromDialog
    )
}