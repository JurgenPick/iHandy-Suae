package com.nssus.ihandy.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.data.constant.AppConstant.MAIN_MENU
import com.nssus.ihandy.data.constant.AppConstant.USER_ROLE
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_SHIPPING
import com.nssus.ihandy.data.extension.simpleVerticalScrollbar
import com.nssus.ihandy.model.home.DisplayHomeModel
import com.nssus.ihandy.model.home.HomeMenuItem
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.DisplayWebViewFloatingButton
import com.nssus.ihandy.ui.basecomposable.MainBgCard
import com.nssus.ihandy.ui.home.basecomposable.MenuCard
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.MainSky
import com.nssus.ihandy.ui.theme.White87

@Composable
fun HomeScreen(
//    menuData: DisplayHomeModel,
    onMenuItemClick: (HomeMenuItem) -> Unit
) {
    val listState = rememberLazyListState()
    println(USER_ROLE)

    Scaffold (
        floatingActionButton = {  if (USER_ROLE == USER_ROLE_SHIPPING) DisplayWebViewFloatingButton(
            url = "http://10.1.4.20:10064/thge21_web/yardhandy/thgeees01/thgeees01init"
        ) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainSky)
                    .padding(innerPadding)
                    .padding(
                        horizontal = Dimens.padding_bg_main,
                        vertical = Dimens.padding_vertical_main_to_content_card
                    )
            ) {
                MainBgCard {
                    Column(modifier = Modifier.background(White87)) {
                        Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
                        BaseHeader(
                            headerId = MAIN_MENU.titleId,
                            style = FontStyles.txt28
                        )
                        Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))

                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp)
                                .simpleVerticalScrollbar(listState),
                        ) {
                            itemsIndexed(MAIN_MENU.menuLs) { index, menu ->
                                MenuCard(
                                    index = index,
                                    data = stringResource(id = menu.menuNameId),
                                    onCardClick = { onMenuItemClick(menu) }
                                )
                                Spacer(modifier = Modifier.height(18.dp))
                            }
                        }
                    }
                }
            }
        }
    )




}

@Preview(showBackground = true, locale = "th")
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onMenuItemClick = {},
//        menuData = DisplayHomeModel(
//            titleId = R.string.home_user_role_packing_title,
//            menuLs = listOf(
//                HomeMenuItem.YardEntryScreen,
//                HomeMenuItem.InventoryTakingScreen,
//                HomeMenuItem.YardEntryScreen,
//                HomeMenuItem.InventoryTakingScreen
//            )
//        )
    )
}