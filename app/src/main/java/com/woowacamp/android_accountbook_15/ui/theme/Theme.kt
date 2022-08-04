package com.woowacamp.android_accountbook_15.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val ColorPalette = darkColors(
    primary = LightPurple,
    primaryVariant = Purple,
    secondary = Yellow,
    secondaryVariant = Red,
    background = OffWhite,
    onPrimary = White,
    onSecondary = White,
    onBackground = Purple
)

@Composable
fun AndroidAccountBook15Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}