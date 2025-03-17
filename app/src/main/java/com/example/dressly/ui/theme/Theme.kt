package com.example.dressly.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Primary Colors (Red-Orange)
val DresslyOrangeRed = Color(0xFF944343) // A strong red-orange (as provided)
val DresslyOrangeRedDark = Color(0xFF7A3434) // Darker shade (slightly adjusted for better contrast)
val DresslyOrangeRedLight = Color(0xFFAD5A5A) // Lighter shade (slightly adjusted for better contrast)

// Secondary Colors (Red Shades)
val DresslySecondary = Color(0xFFFF523D) // Light Red (changed to be ligther)
val DresslySecondaryDark = Color(0xFFA96666) // Dark Red (changed to be dark)

// Neutral Colors (Grays and White)
val DresslyLightGray = Color(0xFFEBEBEB) // Light Gray for surfaces (changed to be more gray)
val DresslyGray = Color(0xFF8A8A8A)
val DresslyDarkGray = Color(0xFF424242) // Dark Gray for text (adjusted to a standard dark gray)
val DresslyWhite = Color(0xFFFFFFFF) // Pure White

// Error Colors (Standard)
val DresslyError = Color(0xFFB00020) // Standard error color (as provided)
val DresslyErrorContainer = Color(0xFFFCD8D9) // Error Container

// Create the Light Color Scheme
val DresslyLightColorScheme = lightColorScheme(
    primary = DresslyOrangeRed,
    onPrimary = DresslySecondary,
    primaryContainer = DresslyOrangeRedLight,
    onPrimaryContainer = DresslyDarkGray,

    secondary = DresslySecondary,
    onSecondary = DresslyDarkGray,
    secondaryContainer = DresslySecondaryDark,
    onSecondaryContainer = DresslyWhite,

    background = DresslyWhite,
    onBackground = DresslyDarkGray,

    surface = DresslyLightGray,
    onSurface = DresslyDarkGray,

    surfaceVariant = DresslyLightGray,
    onSurfaceVariant = DresslyDarkGray,

    error = DresslyError,
    onError = DresslyWhite,
    errorContainer = DresslyErrorContainer,
    onErrorContainer = DresslyDarkGray,
)

// Create the Dark Color Scheme
val DresslyDarkColorScheme = darkColorScheme(
    primary = DresslyOrangeRedDark,
    onPrimary = DresslySecondaryDark,
    primaryContainer = DresslyOrangeRed,
    onPrimaryContainer = DresslyWhite,

    secondary = DresslySecondaryDark,
    onSecondary = DresslyWhite,
    secondaryContainer = DresslySecondary,
    onSecondaryContainer = DresslyDarkGray,

    background = DresslyDarkGray,
    onBackground = DresslyWhite,

    surface = DresslyDarkGray,
    onSurface = DresslyWhite,

    surfaceVariant = DresslyGray,
    onSurfaceVariant = DresslyWhite,

    error = DresslyError,
    onError = DresslyWhite,
    errorContainer = DresslyErrorContainer,
    onErrorContainer = DresslyDarkGray,
)

@Composable
fun DresslyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) DresslyDarkColorScheme else DresslyLightColorScheme
        }
        darkTheme -> DresslyDarkColorScheme
        else -> DresslyLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}