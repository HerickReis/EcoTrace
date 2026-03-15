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

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val QuicksandFamily = FontFamily(
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.Light),
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.Normal),
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.Medium),
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.SemiBold),
    Font(googleFont = GoogleFont("Quicksand"),
        fontProvider = provider,
        weight = FontWeight.Bold),
)
val EcoTraceTypography = Typography(

    headlineMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),

    titleLarge = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),

    titleMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),


    bodyMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),

    labelMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),

    labelLarge = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp
    ),
)