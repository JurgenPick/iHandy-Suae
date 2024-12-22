package com.nssus.ihandy.model.shipping.chargerelo

sealed interface ChargeReloAction {
    object GoBack : ChargeReloAction
    data class TypingCoilNoTextField(val text: String) : ChargeReloAction
    data class TypingYYRRCCTTextField(val text: String) : ChargeReloAction
    data class TypingWidthNoTextField(val text: String) : ChargeReloAction
    object ClickSendButton : ChargeReloAction
    object ClickClearButton : ChargeReloAction
}