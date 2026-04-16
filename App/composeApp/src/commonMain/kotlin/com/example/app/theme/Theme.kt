package com.example.app.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary            = Purple40,
    onPrimary          = Neutral99,
    primaryContainer   = Purple90,
    onPrimaryContainer = Purple10,
    secondary          = Teal40,
    onSecondary        = Neutral99,
    secondaryContainer = Teal90,
    onSecondaryContainer = Teal10,
    tertiary           = Rose40,
    onTertiary         = Neutral99,
    tertiaryContainer  = Rose90,
    onTertiaryContainer = Rose10,
    error              = Error40,
    onError            = Neutral99,
    errorContainer     = Error90,
    onErrorContainer   = Error10,
    background         = Neutral99,
    onBackground       = Neutral10,
    surface            = Neutral99,
    onSurface          = Neutral10,
    surfaceVariant     = NeutralVariant90,
    onSurfaceVariant   = NeutralVariant30,
    outline            = NeutralVariant50,
    outlineVariant     = NeutralVariant80,
)

private val DarkColorScheme = darkColorScheme(
    primary            = Purple80,
    onPrimary          = Purple20,
    primaryContainer   = Purple30,
    onPrimaryContainer = Purple90,
    secondary          = Teal80,
    onSecondary        = Teal20,
    secondaryContainer = Teal30,
    onSecondaryContainer = Teal90,
    tertiary           = Rose80,
    onTertiary         = Rose20,
    tertiaryContainer  = Rose30,
    onTertiaryContainer = Rose90,
    error              = Error80,
    onError            = Error10,
    errorContainer     = Error40,
    onErrorContainer   = Error90,
    background         = Neutral10,
    onBackground       = Neutral90,
    surface            = Neutral10,
    onSurface          = Neutral90,
    surfaceVariant     = NeutralVariant30,
    onSurfaceVariant   = NeutralVariant80,
    outline            = NeutralVariant60,
    outlineVariant     = NeutralVariant30,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = AppTypography,
        content     = content
    )
}
