//package br.com.fiap.ecotrace.ui.theme
package com.ecotrace.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrace.R

// ── Configuração do Google Fonts ──────────────────────────
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val QuicksandFamily = FontFamily(
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.Light),      // 300
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.Normal),     // 400
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.Medium),     // 500
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.SemiBold),   // 600
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.Bold),       // 700
)

// ── Tipografia mapeada às suas telas ─────────────────────
val EcoTraceTypography = Typography(

    // "Olá Letycia" — saudação no header
    // "Conquistas", "Ultimas informações adicionadas"
    headlineMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),

    // Títulos dos cards: "Emissão CO₂ por tipo de transporte"
    titleLarge = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),

    // Título menor: "Transporte", "Alimento" nos cards do perfil
    titleMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),

    // Corpo de texto: parágrafos dos cards, texto de equivalência
    // "Total de emissão, 372kg/mês, isso equivale a..."
    bodyLarge = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),

    // Textos secundários: "Veiculo: Carro", "Distância: 14km"
    bodyMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),

    // Labels: legendas dos gráficos ("Ônibus 40%", "Carro 35%")
    // Labels da navbar
    labelMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),

    // Botões: "Login", "Sign up", "Leia mais aqui"
    labelLarge = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp
    ),
)