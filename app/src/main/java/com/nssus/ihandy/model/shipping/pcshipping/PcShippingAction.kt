package com.nssus.ihandy.model.shipping.pcshipping

interface PcShippingAction {
    object GoBack : PcShippingAction
    //Input keyboard
    data class TypingShipmentLotTextField(val text:String): PcShippingAction
    data class TypingTruckTextField(val text:String): PcShippingAction
    data class TypingCoilNumberTextField(val text:String): PcShippingAction
    data class TypingWidthTextField(val text:String): PcShippingAction
    //Next action
    object ClickNextActionShipmentLot : PcShippingAction
    object ClickNextActionTruck : PcShippingAction
    object ClickNextActionCoilNumber : PcShippingAction
    object ClickNextActionWidth : PcShippingAction
    //Set init
    object InitNavigateData : PcShippingAction
    object SetInitFlagClearAllTextField : PcShippingAction
    object SetInitFlagGetShipmentLotSuccess : PcShippingAction
    object SetInitFlagGetTruckSuccess : PcShippingAction
    object SetInitFlagGetTruckFail : PcShippingAction
    object SetInitGetCoilResp : PcShippingAction
    object SetInitGetWidthSuccess : PcShippingAction
    object SetInitGetWidthFail : PcShippingAction
    object SetInitGetCoilRespWithNeedWidth : PcShippingAction
    //Timer
    object CheckGetDataBeforeClearAll: PcShippingAction
}

interface PcShippingDialogAction {
    object ClickLeftPcShippingDialogButton : PcShippingDialogAction
    object ClickContinueClearAllDialogButton : PcShippingDialogAction
    object ClickSendButton : PcShippingDialogAction
    object ClickClearAllButton : PcShippingDialogAction

}