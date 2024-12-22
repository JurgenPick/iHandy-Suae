package com.nssus.ihandy.model.production.crfhrec


interface CRFHReceiveAction {
    object GoBack : CRFHReceiveAction
    //Input keyboard for each item
    data class TypingCoilNumberTextField(val text: String) : CRFHReceiveAction
    data class TypingYYRRCCSSTextField(val text: String) : CRFHReceiveAction
    data class TypingSupplierNoTextField(val text: String) : CRFHReceiveAction
    //Next action after scan for each item
    object ClickNextActionCoilNoTextField : CRFHReceiveAction
    object ClickNextActionYYRRCCSTextField : CRFHReceiveAction
    object ClickNextActionSupplierNoTextField : CRFHReceiveAction

    object InitNavigateData : CRFHReceiveAction
    object SetInitFlagClearAllTextField : CRFHReceiveAction
    object SetInitFlagGetCoilNumberResp : CRFHReceiveAction
    object SetInitFlagGetYyrrccsdsResp : CRFHReceiveAction
    object SetInitFlagGetSupplierNumberResp : CRFHReceiveAction
}
interface CRFHReceiveDialogAction {
    object ClickSendButton : CRFHReceiveDialogAction
    object ClickClearAllButton : CRFHReceiveDialogAction
}