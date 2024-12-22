package com.nssus.ihandy.data.extension

import com.nssus.ihandy.model.ui.DropdownUIModel

fun List<DropdownUIModel>.setSelectItemFrom(selectedItem: DropdownUIModel) {
    apply {
        // ปรับเป็น ถ้าเจอตัวเดิมไม่ต้องเข้าก็ได้
        find { it.isSelected }?.let { it.isSelected = false }
        find { selectedItem.value == it.value }?.let { it.isSelected = true }
    }
}

fun List<DropdownUIModel>.getSelectedItem(): DropdownUIModel? = find { it.isSelected }

fun List<DropdownUIModel>.getSelectedItemValue(): String? = getSelectedItem()?.value