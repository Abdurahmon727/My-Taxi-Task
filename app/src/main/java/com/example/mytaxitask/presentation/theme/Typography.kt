package com.example.mytaxitask.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mytaxitask.R

private val latoFont = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_light, FontWeight.Light),
)

private val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = latoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = latoFont,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
    ),

    headlineMedium = defaultTypography.headlineMedium.copy(
        color = black,
        fontFamily = latoFont,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = latoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
    ),

    labelSmall = TextStyle(
        fontFamily = latoFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
    )

)