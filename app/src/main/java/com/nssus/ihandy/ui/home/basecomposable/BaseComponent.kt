package com.nssus.ihandy.ui.home.basecomposable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.ui.basecomposable.BaseCard
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@Composable
fun MenuCard(
    index: Int,
    data: String,
    onCardClick: () -> Unit
) {
    BaseCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.size_menu_card_corner))
            .clickable { onCardClick() }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
            text = "${index + 1} : $data",
            style = FontStyles.txt20,
            fontWeight = FontWeight.Medium
        )
    }
}