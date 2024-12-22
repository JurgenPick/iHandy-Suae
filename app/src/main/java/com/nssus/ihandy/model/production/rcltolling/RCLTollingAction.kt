package com.nssus.ihandy.model.production.rcltolling

import com.nssus.ihandy.model.production.cvloading.CVLoadingAction

interface RCLTollingAction {
    object GoBack : RCLTollingAction
    //Input keyboard for each item
    data class TypingCoilNumberTextField(val text: String) : RCLTollingAction
    object InitNavigateData : RCLTollingAction
    //Next action after scan for each item
    object ClickNextActionCoilNoTextField : RCLTollingAction

    object SetInitFlagClearAllTextField : RCLTollingAction
    object SetInitFlagGetCoilNumberResp : RCLTollingAction
}
interface RCLTollingDialogAction {
    object ClickSendButton : RCLTollingDialogAction
    object ClickClearAllButton : RCLTollingDialogAction
}