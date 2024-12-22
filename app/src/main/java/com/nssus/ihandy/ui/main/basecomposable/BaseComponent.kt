package com.nssus.ihandy.ui.main.basecomposable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.White87

@Composable
fun AppBarTitle(
    username: String,
    ip: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.main_app_bar_title, username, ip),
            style = FontStyles.txt16,
            fontWeight = FontWeight.Bold,
            color = White87
        )
    }
}

@Composable
fun LogoutButton(onIconClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .clickable { onIconClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .size(26.dp),
            painter = painterResource(id = R.drawable.ic_main_logout),
            contentDescription = "Logout Icon"
        )
    }
}