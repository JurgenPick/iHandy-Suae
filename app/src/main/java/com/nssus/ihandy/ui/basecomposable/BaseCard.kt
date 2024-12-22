package com.nssus.ihandy.ui.basecomposable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.nssus.ihandy.ui.theme.Black50
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.Sky28

@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Dimens.size_menu_card_corner),
    color: Color = Sky28,
    elevation: CardElevation = CardDefaults.cardElevation(),
    borderWidth: Dp = Dimens.size_card_border_small,
    borderColor: Color = Black50,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color),
        shape = shape,
        elevation = elevation,
        border = BorderStroke(borderWidth, borderColor)
    ) {
        content()
    }
}

@Composable
fun MainBgCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.size_card_corner_large),
        elevation = CardDefaults.cardElevation(Dimens.size_elevation_main_content_card)
    ) {
        content()
    }
}