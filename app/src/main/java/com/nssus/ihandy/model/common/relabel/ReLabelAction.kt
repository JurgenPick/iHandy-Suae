package com.nssus.ihandy.model.common.relabel

import com.nssus.ihandy.model.ui.DropdownUIModel

sealed interface ReLabelAction {
    object GoBack : ReLabelAction
    data class TypingReLabel1TextField(val text: String) : ReLabelAction
    data class TypingReLabel2TextField(val text: String) : ReLabelAction
    object ClickSendButton : ReLabelAction
    object ClickClearButton : ReLabelAction
    data class SelectDataDropdown(val selectedData: DropdownUIModel) : ReLabelAction
}