package com.nssus.ihandy.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.nssus.ihandy.data.constant.AppConstant
import com.nssus.ihandy.data.constant.AppConstant.NETWORK_IP
import com.nssus.ihandy.navigation.MainGraph
import com.nssus.ihandy.ui.main.basecomposable.AppBarTitle
import com.nssus.ihandy.ui.main.basecomposable.LogoutButton
import com.nssus.ihandy.ui.theme.Black50
import com.nssus.ihandy.ui.theme.MainSky

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogoutIconClick: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.height(40.dp),
                title = {
                    AppBarTitle(
                        username = AppConstant.USERNAME.padStart(6,'0'),
                        ip = NETWORK_IP ?: "-"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black50),
                actions = {
                    LogoutButton{ onLogoutIconClick() }
                }
            )
        },
        content = { innerPadding ->
            val focusManager = LocalFocusManager.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainSky)
                    .padding(innerPadding)
                    .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
                        focusManager.clearFocus()
                    }
            ) {
                MainGraph(navController = navController) // comment this if u want to see preview
            }
        }
    )
}

@Preview(showBackground = true, locale = "th")
@Composable
fun MainScreenPreview() {
    MainScreen(
        onLogoutIconClick = {}
    )
}