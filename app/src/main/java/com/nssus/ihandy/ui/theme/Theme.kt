package com.nssus.ihandy.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MainSky,
    secondary = MainSky,
    tertiary = MainSky
)

private val LightColorScheme = lightColorScheme(
    primary = MainSky,
    secondary = MainSky,
    tertiary = MainSky
)

@Composable
fun ProvideCustomValue(
    dimensions: Dimensions,
    fontStyle: CustomFontStyle,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    val fontStyleSet = remember { fontStyle }
    CompositionLocalProvider(
        LocalAppDimens provides dimensionSet,
        LocalAppFontStyle provides fontStyleSet,
        content = content
    )
}

private val LocalAppDimens = staticCompositionLocalOf { defaultDimensions }
private val LocalAppFontStyle = staticCompositionLocalOf { defaultFontStyle }

@Composable
fun IHandyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) LightColorScheme /*DarkColorScheme*/ else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MainSky.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme // darkTheme
        }
    }

    val configuration = LocalConfiguration.current
    val dimens = if (configuration.screenWidthDp <= 480) defaultDimensions else sw480Dimensions
    val typography = if (configuration.screenWidthDp <= 480) defaultTypography else sw480Typography
    val fontStyle = if (configuration.screenWidthDp <= 480) defaultFontStyle else sw480FontStyle

    ProvideCustomValue(dimens, fontStyle) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}

val Dimens: Dimensions
    @Composable
    get() = LocalAppDimens.current

val FontStyles: CustomFontStyle
    @Composable
    get() = LocalAppFontStyle.current