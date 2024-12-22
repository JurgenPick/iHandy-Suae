package com.nssus.ihandy.model.home

import androidx.annotation.StringRes
import com.nssus.ihandy.R

data class DisplayHomeModel(
    @StringRes val titleId: Int = R.string.dash_string,
    val menuLs: List<HomeMenuItem> = emptyList()
)