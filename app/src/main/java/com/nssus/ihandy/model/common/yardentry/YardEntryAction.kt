package com.nssus.ihandy.model.common.yardentry

interface YardEntryAction {
    object GoBack : YardEntryAction
    //Input keyboard
    data class TypingCoilNoTextField(val text: String) : YardEntryAction
    data class TypingYYRRCCTTextField(val text: String) : YardEntryAction
    data class TypingSupplierNoTextField(val text: String) : YardEntryAction
    //Click Next Action textbox
    object ClickNextActionCoilTextField : YardEntryAction
    object ClickNextActionYYRRCCTTextField : YardEntryAction
    object ClickNextActionSupplierNoTextField : YardEntryAction
    //Flag
    object InitNavigateData : YardEntryAction
    object SetInitFlagGetCoilSuccess : YardEntryAction
    object SetInitFlagGetCoilWithSupplierSuccess : YardEntryAction
    object SetInitFlagGetCoilFail : YardEntryAction
    object SetInitFlagGetSupplierSuccess : YardEntryAction
    object SetInitFlagGetSupplierFail : YardEntryAction
    object SetInitFlagGetYYRRCCTSuccess : YardEntryAction
    object SetInitFlagGetYYRRCCTFail : YardEntryAction
    object SetInitFlagClearAllTextField : YardEntryAction


    object ClickSendButton : YardEntryAction
}

interface YardEntryDialogAction {
    object ClickClearAll : YardEntryDialogAction
    object ClickContinueClearAll : YardEntryDialogAction
    object ClickConfirmReserved : YardEntryDialogAction
    object ClickCancelReserved : YardEntryDialogAction
    object ClickLeftYardEntryDialogButton : YardEntryDialogAction
    object ClickConfirmSendMQ : YardEntryDialogAction
}