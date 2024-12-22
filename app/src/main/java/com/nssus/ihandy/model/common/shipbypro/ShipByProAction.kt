package com.nssus.ihandy.model.common.shipbypro

import com.nssus.ihandy.model.shipping.pcshipping.CoilDetail

interface ShipByProAction {
    object GoBack : ShipByProAction
    //Input Keyboard
    data class TypingShipmentLotTextField(val text: String): ShipByProAction
    data class TypingCoilNumberTextField(val text: String): ShipByProAction
    //Next action
    object ClickNextActionShipmentLotTextField : ShipByProAction
    object ClickNextActionCoilNumberTextField : ShipByProAction
    object ClickButtonCountCoil : ShipByProAction
    //Coil List Screen
    object ClickBackToShipByPro : ShipByProAction
    data class SelectCoil(val selectData : GetCheckCoilResponse) : ShipByProAction
    //Set Init
    object InitNavigateData : ShipByProAction
    object SetInitFlagClearAllTextField : ShipByProAction
    object SetInitFlagGetShipmentLotRespSuccess : ShipByProAction
    object SetInitFlagGetCoilNumberResp : ShipByProAction

}

interface ShipByProDialogAction{
    object ClickLeftShipByProDialogButton : ShipByProDialogAction
    object ClickContinueDialogButton :ShipByProDialogAction
    object ClickSendButton : ShipByProDialogAction
    object ClickClearAllButton : ShipByProDialogAction
    object ClickConfirmSendMQ : ShipByProDialogAction
    //Action Dialog in Coil List Screen
    object ClickLeftDeleteCoilDialogButton : ShipByProDialogAction
    object ClickRightDeleteCoilDialogButton : ShipByProDialogAction

}