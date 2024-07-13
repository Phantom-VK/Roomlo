package com.app.roomlo.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.roomlo.R
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.ui.theme.interFont

@Preview(showBackground = true)
@Composable
fun RoomItemView(/*TODO Add parameters*/) {
    OutlinedCard(
        shape = RoundedCornerShape(MaterialTheme.dimens.small2),
        modifier = Modifier
            .wrapContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color(0xFFC2C2C2))
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
                    .width(MaterialTheme.dimens.large+20.dp)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.width(2.dp))

            // Information and actions section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = MaterialTheme.dimens.small1)
            ) {
                // Information section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.dimens.small2),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rs.1200/month (per person)",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(bottom = MaterialTheme.dimens.extraSmall),
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Double sharing",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(bottom = MaterialTheme.dimens.extraSmall),
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "200 Sq.ft",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(bottom = MaterialTheme.dimens.extraSmall),
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontFamily = interFont,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Owner: Giradkar",
                        color = MaterialTheme.colorScheme.background,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
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
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Favorite Room",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
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
                        Text(text = "Book", color = MaterialTheme.colorScheme.surface,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize)
                    }
                }
            }
        }
    }
}
