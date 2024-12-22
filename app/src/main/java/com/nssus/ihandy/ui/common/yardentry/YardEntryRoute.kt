package com.nssus.ihandy.ui.common.yardentry

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
import com.nssus.ihandy.ui.basecomposable.BaseMsgWithButtonDialog
import com.nssus.ihandy.ui.basecomposable.CompleteDialog
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.basecomposable.WarningDialog
import com.nssus.ihandy.ui.common.yardentry.viewmodel.YardEntryViewModel
import com.nssus.ihandy.ui.theme.Black50
import com.nssus.ihandy.ui.theme.WarningRed

@Composable
fun YardEntryRoute(
    navController: NavController,
    yardEntryVm: YardEntryViewModel
) {
    val uiYardEntrySt by yardEntryVm.yardEntryUISt

    LaunchedEffect(Unit) {
        yardEntryVm.initData()
    }
    when {
        uiYardEntrySt.isLoading -> CustomLoading()
        uiYardEntrySt.isSuccess -> {
            when (uiYardEntrySt.navigateType) {
                YardEntryNavigateType.GO_BACK -> {
                    navController.popBackStack()
                    yardEntryVm.action(YardEntryAction.InitNavigateData)
                }
                YardEntryNavigateType.DISPLAY_CLEAR_ALL_DIALOG -> {
                    WarningDialog(
                        message = uiYardEntrySt.successMsg ?: " ",
                        onLeftDialogButtonClick = { yardEntryVm.actionFromDialog(YardEntryDialogAction.ClickContinueClearAll) },
                        onRightDialogButtonClick = {yardEntryVm.action(YardEntryAction.InitNavigateData)})
                }
                YardEntryNavigateType.RESERVED_COIL_NO -> {
                    WarningDialog(
                        message = uiYardEntrySt.successMsg ?: " ",
                        onLeftDialogButtonClick = {yardEntryVm.actionFromDialog(YardEntryDialogAction.ClickConfirmReserved)},
                        onRightDialogButtonClick = {yardEntryVm.actionFromDialog(YardEntryDialogAction.ClickCancelReserved)})
                }
                YardEntryNavigateType.DISPLAY_SUCCESS -> {
                    CompleteDialog(
                        message = uiYardEntrySt.successMsg ?: " ",
                        onDialogButtonClick = {yardEntryVm.actionFromDialog(YardEntryDialogAction.ClickConfirmSendMQ)})
                }
                else -> {
                    println("isSuccess else")
                }
            }
        }
        uiYardEntrySt.isError -> {
            BaseMsgWithButtonDialog(
                message = (if (uiYardEntrySt.errorBody?.errorMsg.isNullOrEmpty()) stringResource(id = uiYardEntrySt.errorBody?.yardEntryErrorType?.errorMsgId ?: R.string.dash_string)
                else uiYardEntrySt.errorBody?.errorMsg) ?: "-" ,
                icon = R.drawable.ic_dialog_red_cross,
                messageColor = Black50,
                leftButtonTextColor = Color.White,
                leftButtonColor = WarningRed,
                leftButtonBorderColor = WarningRed,
                isHasPairButton = !uiYardEntrySt.isClickNextActionCoilNo && uiYardEntrySt.atErrorInYardTextBox,
                onLeftButtonClick = { yardEntryVm.actionFromDialog(YardEntryDialogAction.ClickLeftYardEntryDialogButton) },
                onRightButtonClick = {yardEntryVm.action(YardEntryAction.InitNavigateData)}
            )
        }
    }

    YardEntryScreen(
        uiYardEntrySt = uiYardEntrySt,
        onAction = yardEntryVm::action,
        onDialogAction = yardEntryVm::actionFromDialog
    )
}