package com.nssus.ihandy.model.ui

data class DropdownUIModel(
    val value: String, // value to use
    val display: String, // display on dropdown
    var isSelected: Boolean = false // flag
)