package com.nssus.ihandy.model.production.cvloading

import com.nssus.ihandy.model.common.shipbypro.ShipByProAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProDialogAction
import com.nssus.ihandy.model.common.yardentry.YardEntryAction
import com.nssus.ihandy.model.common.yardentry.YardEntryDialogAction


interface CVLoadingAction {

    object GoBack : CVLoadingAction
    //Input keyboard for each item
    data class TypingCoilNoTextField(val text: String) : CVLoadingAction
    data class TypingLCSTextField(val text: String) : CVLoadingAction
    data class TypingSupplierNoTextField(val text: String) : CVLoadingAction
    //Next action after scan for each item
    object ClickNextActionCoilNoTextField : CVLoadingAction
    object ClickNextActionLCSTextField : CVLoadingAction
    object ClickNextActionSupplierNoTextField : CVLoadingAction


    //button action
    object ClickSendButton : CVLoadingAction
    object ClickClearButton : CVLoadingAction

    object SetInitFlagClearAllTextField : CVLoadingAction
    object SetInitFlagGetCoilNumberResp : CVLoadingAction
    object SetInitFlagGetLineSkidResp : CVLoadingAction
    object SetInitFlagGetSupplierNumberResp : CVLoadingAction
    object SetInitFlagGetCoilSuccess : CVLoadingAction
    object SetInitFlagGetCoilFail : CVLoadingAction
    object SetInitFlagGetSupplierSuccess : CVLoadingAction
    object SetInitFlagGetSupplierFail : CVLoadingAction
    object ClickClearAll : CVLoadingAction
    object ClickContinueClearAll : CVLoadingAction

    //set init
    object InitNavigateData : CVLoadingAction

}

interface CVLoadingDialogAction {

    object ClickContinueClearAll : CVLoadingDialogAction
    object ClickLeftCVLoadingDialogButton : CVLoadingDialogAction
    object ClickContinueDialogButton : CVLoadingDialogAction
    object ClickConfirmSendMQ : CVLoadingDialogAction
    object ClickSendButton : CVLoadingDialogAction
    object ClickClearAllButton : CVLoadingDialogAction

}