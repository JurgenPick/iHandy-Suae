package com.nssus.ihandy.ui.production.crfhrec

import CRFHReceiveScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveAction
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveDialogAction
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveNavigateType
import com.nssus.ihandy.ui.basecomposable.BaseMsgWithButtonDialog
import com.nssus.ihandy.ui.basecomposable.CompleteDialog
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.production.crfhrec.viewmodel.CRFHReceiveViewModel
@Composable
fun CRFHReceiveRoute(
    navController: NavController,
    crfhReceiveVM: CRFHReceiveViewModel
) {
    val uiCRFHReceiveSt by crfhReceiveVM.crfhReceiveUISt
    // Initialize data when the route is loaded
    LaunchedEffect(Unit) {
        crfhReceiveVM.initData()
    }
    // Handle various UI states and navigation events
    when {
        uiCRFHReceiveSt.isLoading -> CustomLoading()
        uiCRFHReceiveSt.isError -> {
            BaseMsgWithButtonDialog(
                message = uiCRFHReceiveSt.message.orEmpty(),
                icon = R.drawable.ic_dialog_red_cross,
                onLeftButtonClick = { crfhReceiveVM.action(CRFHReceiveAction.InitNavigateData) }
            )
        }
        uiCRFHReceiveSt.isSuccess -> {
            when (uiCRFHReceiveSt.navigateType) {
                CRFHReceiveNavigateType.GO_BACK -> {
                    navController.popBackStack()
                    crfhReceiveVM.action(CRFHReceiveAction.InitNavigateData)
                }
                CRFHReceiveNavigateType.DISPLAY_DIALOG_SUCCESS -> {
                    CompleteDialog(
                        message = uiCRFHReceiveSt.message.orEmpty(),
                        onDialogButtonClick = { crfhReceiveVM.action(CRFHReceiveAction.InitNavigateData) }
                    )
                }
                else -> {}
            }
        }
    }
    // Render the CRFH Receive screen
    CRFHReceiveScreen(
        uiCRFHReceiveSt = uiCRFHReceiveSt,
        onAction = crfhReceiveVM::action,
        onDialogAction = crfhReceiveVM::actionFormDialog
    )
}