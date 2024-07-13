package com.app.roomlo.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.roomlo.R
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.ui.theme.interFont

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

    OutlinedCard(
        shape = RoundedCornerShape(MaterialTheme.dimens.small2),
        modifier = Modifier
            .wrapContentSize()
            .padding(MaterialTheme.dimens.extraSmall),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, Color(0xFFC2C2C2))
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                HorizontalPager(state = pagerState) { page ->
                    Image(
                        painter = imageSlider[page],
                        contentDescription = "Room Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(MaterialTheme.dimens.small2))
                    )
                }
            }

            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small2))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MaterialTheme.dimens.small1)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rs.1200/month (per person)",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Double sharing",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "200 Sq.ft",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Owner: Giradkar",
                        color = MaterialTheme.colorScheme.background,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontFamily = interFont,
                        fontWeight = FontWeight.SemiBold
                    )
                }

//                Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraSmall))

                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Favorite Room",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(MaterialTheme.dimens.medium2)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
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
                            color = MaterialTheme.colorScheme.surface,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    }
                }
            }
        }
    }
}

