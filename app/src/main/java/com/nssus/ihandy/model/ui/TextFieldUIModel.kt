package com.nssus.ihandy.model.ui

import androidx.compose.ui.graphics.Color
import com.nssus.ihandy.ui.theme.MainGray
import com.nssus.ihandy.ui.theme.Red

enum class TextFieldType {
    Number,
    Text,
    Password
}

data class TextFieldColor(
    val enableColor: Color = Color.White,
    val disableColor: Color = MainGray,
    val textColor: Color = Color.Black,
    val placeHolderColor: Color = MainGray,
    val borderColor: Color = Color.Black,
    val errorBorderColor: Color = Red, //
    val supportMsgColor: Color = Color.Black,
    val errorMsgColor: Color = Red

)

enum class TextFieldAlign {
    Start,
    End
}