package com.nssus.ihandy.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import com.nssus.ihandy.R

val poppinsFonts = FontFamily(
    Font(R.font.poppins_thin, FontWeight.Thin),
    Font(R.font.poppins_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.poppins_extra_light, FontWeight.ExtraLight),
    Font(R.font.poppins_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.poppins_regular),
    Font(R.font.poppins_italic, style = FontStyle.Italic),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
    Font(R.font.poppins_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_black_italic, FontWeight.Black, FontStyle.Italic),
)

class CustomFontStyle(
    val txt14: TextStyle,
    val txt16: TextStyle,
    val txt20: TextStyle,
    val txt22: TextStyle,
    val txt24: TextStyle,
    val txt26: TextStyle,
    val txt28: TextStyle,
    val txt30: TextStyle,
    val txt32: TextStyle,
    val txt34: TextStyle,
    val txt36: TextStyle,
    val txt38: TextStyle,
    val txt40: TextStyle,
    val txt42: TextStyle,
    val txt44: TextStyle
)

val defaultFontStyle = CustomFontStyle(
    txt14 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 14.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt16 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 16.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt20 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 20.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt22 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 22.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt24 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 24.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt26 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 26.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt28 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 28.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt30 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 30.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt32 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 32.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt34 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 34.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt36 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 36.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt38 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 38.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt40 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 40.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt42 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 42.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    ),
    txt44 = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 44.sp,
        baselineShift = BaselineShift(-0.2f),
        color = Color.Black
    )
)

// ยังใช้ขนาดเดียวกับจอเล็ก
val sw480FontStyle = defaultFontStyle

val defaultTypography = Typography(
    titleSmall = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 18.sp
    ),
    titleMedium = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 32.sp
    ),
    bodySmall = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = poppinsFonts,
        fontSize = 20.sp
    )
)

// ยังใช้ขนาดเดียวกับจอเล็ก
val sw480Typography = defaultTypography