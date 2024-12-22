package com.nssus.ihandy.ui.common.shipbypro.coillist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.model.common.shipbypro.ShipByProAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProUIStateModel
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.MainGray

@Composable
fun ShipByProCoilListScreen(
    uiShipByProSt : ShipByProUIStateModel,
    onAction : (ShipByProAction) -> Unit
){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(vertical = 20.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            IconButton(
                modifier = Modifier.size(44.dp),
                onClick = {onAction(ShipByProAction.ClickBackToShipByPro)}
            ){
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color.Black
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiShipByProSt.resCoilList.isEmpty()){
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        text = stringResource(id = R.string.ship_by_pro_list_coil_empty),
                        style = FontStyles.txt20,
                        textAlign = TextAlign.Center
                    )
                }
            } else{
                items(uiShipByProSt.resCoilList) { coilListItem ->
                    Column(modifier = Modifier.clickable { onAction(ShipByProAction.SelectCoil(coilListItem)) }) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            text = coilListItem.coilNo ?: "",
                            style = FontStyles.txt20,
                            color = Color.Black
                        )
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth(),
                            color = MainGray
                        )
                    }
                }
            }
        }
    }

}