package com.example.roomlo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomlo.ui.theme.interFont

@Composable
fun RoomItemView() {
    OutlinedCard(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .wrapContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(2.dp, Color(0xFFC2C2C2))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Image section
            Image(
                painter = painterResource(id = R.drawable.the_taj_mahal_architectural_digest),
                contentDescription = "Room Image",
                modifier = Modifier
                    .width(130.dp)
                    .aspectRatio(1f)

            )

            Spacer(modifier = Modifier.width(10.dp))

            // Information and actions section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 10.dp)
            ) {
                // Information section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rs.1200/month (per person)",
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(bottom = 1.dp),
                        fontSize = 12.sp,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Double sharing",
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(bottom = 1.dp),
                        fontSize = 12.sp,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "200 Sq.ft",
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(bottom = 1.dp),
                        fontSize = 12.sp,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Owner: Giradkar",
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        fontFamily = interFont,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Action section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share Room",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Favorite Room",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Locate Room",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(text = "Book", color = MaterialTheme.colorScheme.surface)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRoomItemView() {
    RoomItemView()
}
