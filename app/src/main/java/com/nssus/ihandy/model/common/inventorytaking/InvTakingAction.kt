package com.nssus.ihandy.model.common.inventorytaking

import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloAction

interface InvTakingAction {
    object GoBack : InvTakingAction
    data class TypingCoilNoTextField(val text: String) : InvTakingAction
    data class TypingYYRRCCTTextField(val text: String) : InvTakingAction
    data class TypingSupplierNoTextField(val text: String) : InvTakingAction
    object ClickClearButton : InvTakingAction
    object ClickSendButton : InvTakingAction
}