package com.app.roomlo.screens.components

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.roomlo.R
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.ui.theme.interFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun RoomItemView() {
    val imageSlider = listOf(
        painterResource(id = R.drawable.rooom_img_1),
        painterResource(id = R.drawable.room_img_2),
        painterResource(id = R.drawable.room_img_3)
    )
    val pagerState = rememberPagerState(pageCount = { imageSlider.size })
    val coroutineScope = rememberCoroutineScope()
    var isHovered by remember { mutableStateOf(false) }

    LaunchedEffect(isHovered) {
        if (isHovered) {
            while (true) {
                yield()
                delay(1500)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % pagerState.pageCount,
                    animationSpec = tween(
                        durationMillis = 600, // Adjust the duration for slower animation
                        easing = EaseInOut
                    )
                )
            }
        }
    }

    OutlinedCard(
        shape = RoundedCornerShape(MaterialTheme.dimens.small2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .wrapContentHeight(),
        border = BorderStroke(1.dp, Color(0xFFC2C2C2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            SlidingImages(coroutineScope, pagerState, imageSlider)


            Column(
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isHovered = true
                                tryAwaitRelease()
                                isHovered = false
                            }
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Rs.1200/month (per person)\nDouble sharing 200 Sq.ft",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Light
                    )

                    Text(
                        text = "Rs.2400(2 people, 1 room)",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Justify,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(MaterialTheme.dimens.medium2)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share Room",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(MaterialTheme.dimens.medium2)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_favorite_border_24),
                            contentDescription = "Favorite Room",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(MaterialTheme.dimens.medium2)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Locate Room",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(
                            text = "Book",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun SlidingImages(
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    imageSlider: List<Painter>
) {


        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
            contentDescription = "Previous Image",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(MaterialTheme.dimens.small3)
                .clickable {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = (pagerState.currentPage - 1 + imageSlider.size) % imageSlider.size
                        )
                    }
                }
        )

        Box(
            modifier = Modifier
                .size(width = MaterialTheme.dimens.large+30.dp, height = MaterialTheme.dimens.large)
        ) {
            HorizontalPager(state = pagerState) { page ->
                Image(
                    painter = imageSlider[page],
                    contentDescription = "Room Image",
                    modifier = Modifier
                        .size(width = MaterialTheme.dimens.large+30.dp,height = MaterialTheme.dimens.large),
                    contentScale = ContentScale.Crop

                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
            contentDescription = "Next Image",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(MaterialTheme.dimens.small3)
                .clickable {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = (pagerState.currentPage + 1) % imageSlider.size
                        )
                    }
                }
        )

    }


