package com.nssus.ihandy.ui.common.relabel.util

import com.nssus.ihandy.model.ui.DropdownUIModel

object ReLabelUtil {

    fun getDataLs(): List<DropdownUIModel> = listOf(
        DropdownUIModel(
            value = "12",
            display = "Normal Operation",
            isSelected = true
        ),
        DropdownUIModel(
            value = "13",
            display = "SPCS Recovery"
        ),
        DropdownUIModel(
            value = "14",
            display = "Return Coil"
        ),
        DropdownUIModel(
            value = "15",
            display = "Product Label"
        )

    )

}