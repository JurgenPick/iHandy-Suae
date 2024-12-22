package com.nssus.ihandy.model.production.delcheck

import com.nssus.ihandy.model.production.cvloading.CVLoadingAction

interface DeliveryCheckAction {
    object GoBack : DeliveryCheckAction

    //Input keyboard for each item
    data class TypingCoilNumberTextField(val text: String) : DeliveryCheckAction

    //Next action after scan for each item
    object ClickNextActionCoilNoTextField : DeliveryCheckAction

    object InitNavigateData : DeliveryCheckAction
    object SetInitFlagClearAllTextField : DeliveryCheckAction
    object SetInitFlagGetCoilNumberResp : DeliveryCheckAction
}
interface DeliveryCheckDialogAction {
    object ClickSendButton : DeliveryCheckDialogAction
    object ClickClearAllButton : DeliveryCheckDialogAction
}