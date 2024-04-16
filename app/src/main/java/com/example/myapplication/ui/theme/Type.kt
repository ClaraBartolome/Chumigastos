package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


val openSansFontFamily = FontFamily(
    Font(R.font.opensans_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.opensans_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.opensans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.opensans_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.opensans_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.opensans_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.opensans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.opensans_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.opensans_extrabold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.opensans_regular, FontWeight.Normal, FontStyle.Normal),
)


val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.poppins_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.poppins_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.poppins_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.poppins_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.poppins_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.poppins_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.poppins_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.poppins_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.poppins_extrabold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.poppins_extrabold_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.poppins_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.poppins_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.poppins_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.poppins_thin_italic, FontWeight.Thin, FontStyle.Italic),
)

private val defaultTypography = Typography()

val CustomTypography = Typography(
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = openSansFontFamily),
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = openSansFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = openSansFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = openSansFontFamily),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = openSansFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = openSansFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = openSansFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = openSansFontFamily),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = openSansFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = openSansFontFamily),
    bodyMedium =defaultTypography.bodyMedium.copy(fontFamily = openSansFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = openSansFontFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = openSansFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = openSansFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = openSansFontFamily),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)