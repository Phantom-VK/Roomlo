package com.example.roomlo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.roomlo.R


val baloo = FontFamily(

    Font(R.font.baloo2_bold, FontWeight.Bold),
    Font(R.font.baloo2_extrabold, FontWeight.ExtraBold),
    Font(R.font.baloo2_medium, FontWeight.Medium),
    Font(R.font.baloo2_semibold, FontWeight.SemiBold),
    Font(R.font.baloo2_regular)


)

val interFont = FontFamily(
    Font(R.font.inter_black, FontWeight.Black),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_regular, FontWeight.Normal),
)



// Set of Material typography styles to start with
val CompactTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)

val CompactMediumTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)

val CompactSmallTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleMedium = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)

val MediumTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 38.sp
    ),
    titleMedium = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)

val ExpandedTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 42.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = baloo,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp
    ),
    titleMedium = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    )
)