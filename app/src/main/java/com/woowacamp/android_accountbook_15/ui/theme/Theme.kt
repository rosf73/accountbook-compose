package com.woowacamp.android_accountbook_15.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = LightPurple,
    primaryVariant = Purple,
    secondary = Yellow,
    secondaryVariant = Red,
    background = OffWhite,
    onPrimary = White,
    onSecondary = White,
    onBackground = Purple
)

private val LightColorPalette = lightColors(
    primary = LightPurple,
    primaryVariant = Purple,
    secondary = Yellow,
    secondaryVariant = Red,
    background = OffWhite,
    onPrimary = White,
    onSecondary = White,
    onBackground = Purple

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AndroidAccountBook15Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}