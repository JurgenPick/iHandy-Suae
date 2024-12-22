package com.nssus.ihandy.ui.basecomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.White87

@Composable
fun BaseContentCardWithBackButton(
    onBackIconClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(modifier = Modifier.padding(vertical = Dimens.padding_vertical_main_to_content_card)) {
        BackImageIcon { onBackIconClick() }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            BaseWhiteBgMainContentCard {
                content()
            }
        }
        Spacer(modifier = Modifier.width(Dimens.padding_bg_with_back_btn))
    }
}

@Composable
fun BaseWhiteBgMainContentCard(content: @Composable ColumnScope.() -> Unit) {
    MainBgCard {
        Column(
            modifier = Modifier
                .background(White87)
                .fillMaxSize()
                .padding(horizontal = Dimens.padding_card_to_content)
        ) {
            content()
        }
    }
}