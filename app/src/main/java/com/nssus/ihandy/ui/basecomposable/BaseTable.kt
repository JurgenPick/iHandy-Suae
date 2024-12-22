package com.nssus.ihandy.ui.basecomposable

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.ui.theme.BaseGreen
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.SilverGray
import com.nssus.ihandy.ui.theme.StatusRed
import com.nssus.ihandy.ui.theme.White87


@Composable
fun Table2ColumnHeaderRow(
    firstColumnWeight: Float = .7f,
    secondColumnWeight: Float = .3f,
    rowBackgroundColor: Color = SilverGray,
    @StringRes firstTitleId: Int = R.string.common_coil_no_table_title,
    @StringRes secondTitleId: Int = R.string.common_status_table_title
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(rowBackgroundColor)
    ) {
        Text(
            modifier = Modifier.weight(firstColumnWeight),
            text = stringResource(id = firstTitleId),
            style = FontStyles.txt20,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(secondColumnWeight),
            text = stringResource(id = secondTitleId),
            style = FontStyles.txt20,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Table2ColumnDetailRow(
    firstColumnWeight: Float = .7f,
    secondColumnWeight: Float = .3f,
    rowBackgroundColor: Color = SilverGray,
    isFirstColumnValueMatched: Boolean, // ถ้าไม่ใช้ ค่อยลบออก
    firstColumnValue: String,
    secondColumnValue: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(rowBackgroundColor)
            .padding(vertical = 6.dp)
            .height(IntrinsicSize.Min)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(firstColumnWeight),
            text = firstColumnValue,
            style = FontStyles.txt16,
            fontWeight = FontWeight.Medium,
            color = if (isFirstColumnValueMatched) BaseGreen else Color.Black
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(secondColumnWeight)
        ) {
            Text(
                modifier = Modifier
                    .background(White87)
                    .fillMaxSize(),
                text = secondColumnValue,
                style = FontStyles.txt16,
                fontWeight = FontWeight.Bold,
                color = StatusRed,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Table3ColumnHeaderRow(
    firstColumnWeight: Float = .5f,
    secondColumnWeight: Float = .25f,
    thirdColumnWeight: Float = .25f,
    @StringRes firstTileId: Int = R.string.common_coil_no_table_title,
    @StringRes secondTileId: Int = R.string.common_status_table_title,
    @StringRes thirdTileId: Int = R.string.common_dock_table_title
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(SilverGray)
    ) {
        Text(
            modifier = Modifier.weight(firstColumnWeight),
            text = stringResource(id = firstTileId),
            style = FontStyles.txt20,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(secondColumnWeight),
            text = stringResource(id = secondTileId),
            style = FontStyles.txt20,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(thirdColumnWeight),
            text = stringResource(id = thirdTileId),
            style = FontStyles.txt20,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Table3ColumnDetailRow(
    firstColumnWeight: Float = .5f,
    secondColumnWeight: Float = .25f,
    thirdColumnWeight: Float = .25f,
    isFirstColumnValueMatched: Boolean,
    firstColumnValue: String,
    secondColumnValue: String,
    thirdColumnValue: String,
    rowBottomSpace: Dp = 10.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            modifier = Modifier.weight(firstColumnWeight),
            text = firstColumnValue,
            style = FontStyles.txt16,
            fontWeight = FontWeight.Medium,
            color = if (isFirstColumnValueMatched
                || secondColumnValue == "OK") BaseGreen else Color.Black
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier
                .background(White87)
                .padding(horizontal = 4.dp)
                .fillMaxHeight()
                .weight(secondColumnWeight),
            text = secondColumnValue,
            style = FontStyles.txt16,
            fontWeight = FontWeight.Bold,
            color = if(secondColumnValue == "OK") BaseGreen else StatusRed,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier
                .background(White87)
                .padding(horizontal = 4.dp)
                .fillMaxHeight()
                .weight(thirdColumnWeight),
            text = thirdColumnValue,
            style = FontStyles.txt16,
            fontWeight = FontWeight.Bold,
            color = if(secondColumnValue == "OK") BaseGreen else StatusRed,
            textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.height(rowBottomSpace))
}