package com.example.roomlo.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val extraSmall: Dp = 4.dp,
    val small1: Dp = 8.dp,
    val small2: Dp = 12.dp,
    val small3: Dp = 16.dp,
    val medium1: Dp = 20.dp,
    val medium2: Dp = 24.dp,
    val medium3: Dp = 28.dp,
    val large: Dp = 32.dp,
    val buttonHeight: Dp = 44.dp, // Increased by 4 dp
    val logoSize: Dp = 46.dp // Increased by 4 dp
)

val CompactSmallDimens = Dimens(
    small1 = 4.dp,
    small2 = 8.dp,
    small3 = 12.dp,
    medium1 = 16.dp,
    medium2 = 20.dp,
    medium3 = 24.dp,
    large = 104.dp, // Increased to be above 100 dp
    buttonHeight = 34.dp, // Increased by 4 dp
    logoSize = 40.dp // Increased by 4 dp
)

val CompactMediumDimens = Dimens(
    small1 = 6.dp,
    small2 = 10.dp,
    small3 = 14.dp,
    medium1 = 18.dp,
    medium2 = 22.dp,
    medium3 = 26.dp,
    large = 110.dp, // Increased to be above 100 dp
    buttonHeight = 40.dp, // Increased by 4 dp
    logoSize = 44.dp // Increased by 4 dp
)

val CompactDimens = Dimens(
    small1 = 8.dp,
    small2 = 12.dp,
    small3 = 16.dp,
    medium1 = 20.dp,
    medium2 = 24.dp,
    medium3 = 28.dp,
    large = 120.dp, // Increased to be above 100 dp
    buttonHeight = 44.dp, // Increased by 4 dp
    logoSize = 48.dp // Increased by 4 dp
)

val MediumDimens = Dimens(
    small1 = 10.dp,
    small2 = 14.dp,
    small3 = 18.dp,
    medium1 = 22.dp,
    medium2 = 26.dp,
    medium3 = 30.dp,
    large = 130.dp, // Increased to be above 100 dp
    buttonHeight = 48.dp, // Increased by 4 dp
    logoSize = 52.dp // Increased by 4 dp
)

val ExpandedDimens = Dimens(
    small1 = 12.dp,
    small2 = 16.dp,
    small3 = 20.dp,
    medium1 = 24.dp,
    medium2 = 28.dp,
    medium3 = 32.dp,
    large = 140.dp, // Increased to be above 100 dp
    buttonHeight = 52.dp, // Increased by 4 dp
    logoSize = 56.dp // Increased by 4 dp
)
