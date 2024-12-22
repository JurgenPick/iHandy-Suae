package com.nssus.ihandy.ui.basecomposable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.model.ui.TextFieldColor
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.MainGray
import com.nssus.ihandy.ui.theme.MainSky

@Composable
fun BaseDropdownDialog(
    modifier: Modifier = Modifier,
    dataList: List<DropdownUIModel>,
    onDropdownItemSelected: (DropdownUIModel) -> Unit,
    selectedItem: DropdownUIModel? = null,
    tfColor: TextFieldColor = TextFieldColor(),
    placeholder: String? = null,
    style: TextStyle = FontStyles.txt22.copy(color = Color.Black)
) {
    var isOpenDialog by remember { mutableStateOf(false) }

    // TextField to Display Dialog
    BaseTextFieldWithTrailingIcon(
        modifier = modifier
            .fillMaxWidth()
            .clickable { isOpenDialog = !isOpenDialog },
        value = TextFieldValue(selectedItem?.display ?: ""),
        placeholder = placeholder,
        enabled = false,
        trailingIcon = {
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = Dimens.padding_normal),
                painter = painterResource(id = R.drawable.ic_arrow_dropdown),
                contentDescription = "Dropdown Icon"
            )
        },
        style = style,
        color = tfColor
    )

    // Dialog UI
    if (isOpenDialog) {
        BaseDialog(onTouchOutsideDialog = { isOpenDialog = false }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp) // outside-dialog's horizontal space
                    .background(MainSky, RoundedCornerShape(Dimens.size_dropdown_dialog_corner))
                    .padding(vertical = 20.dp) // inner-dialog's space
            ) {
                items(dataList) {
                    Column(
                        modifier = Modifier.clickable {
                            onDropdownItemSelected(it)
                            isOpenDialog = false
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 24.dp),
                            text = it.display,
                            style = FontStyles.txt22.copy(color = style.color),
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