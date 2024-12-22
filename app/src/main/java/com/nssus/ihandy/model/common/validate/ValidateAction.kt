package com.nssus.ihandy.model.common.validate

sealed interface ValidateAction {
    object GoBack : ValidateAction
    data class TypingValidate1TextField(val text: String) : ValidateAction
    data class TypingValidate2TextField(val text: String) : ValidateAction
    data class TypingValidate3TextField(val text: String) : ValidateAction
    object ClickSendButton : ValidateAction
    object ClearAllValueButton : ValidateAction
    object ClickNextActionValidate1TextField : ValidateAction
    object SetInitFlagGetValidate1Resp : ValidateAction
    object ClickNextActionValidate2TextField : ValidateAction
    object SetInitFlagGetValidate2Resp : ValidateAction
    object ClickNextActionValidate3TextField : ValidateAction
    object SetInitFlagGetValidate3Resp : ValidateAction
    object SetInitFlagClearAllTextField : ValidateAction
}