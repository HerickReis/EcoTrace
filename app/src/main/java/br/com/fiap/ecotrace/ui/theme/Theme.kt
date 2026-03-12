package com.ecotrace.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ── Esquema Claro (o que suas telas mostram) ──────────────
private val LightColorScheme = lightColorScheme(
    // A cor principal do app — botões primários, FAB, badges
    primary = EcoGreen,

    // Texto/ícone sobre elementos "primary"
    onPrimary = EcoWhite,

    // Variação mais escura do primary — hover, pressed states
    primaryContainer = EcoGreenDark,
    onPrimaryContainer = EcoWhite,

    // Cor secundária — detalhes, chips, elementos de apoio
    secondary = ChartYellow,
    onSecondary = EcoTextPrimary,

    // Fundo geral de todas as telas
    background = EcoBackground,
    onBackground = EcoTextPrimary,

    // Fundo de cards e superfícies elevadas
    surface = EcoWhite,
    onSurface = EcoTextPrimary,

    // Variação de surface para cards com fundo cinza
    surfaceVariant = EcoGrayLight,
    onSurfaceVariant = EcoGrayText,

    // Contorno de botões outline (botão Login)
    outline = EcoGreenMedium,
)

// ── Esquema Escuro (opcional no MVP, mas boa prática) ─────
private val DarkColorScheme = darkColorScheme(
    primary = EcoGreenMedium,
    onPrimary = EcoGreenDark,
    background = Color(0xFF121812), // Verde quase preto
    onBackground = EcoWhite,
    surface = Color(0xFF1E2D1E),
    onSurface = EcoWhite,
)

// ── Composable principal do tema ──────────────────────────
@Composable
fun EcoTraceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = EcoTraceTypography, // vem do Type.kt
        content = content
    )
}