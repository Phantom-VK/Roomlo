package com.app.roomlo.screens.components

import android.net.Uri
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.app.roomlo.R
import com.app.roomlo.dataclasses.Property
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.ui.theme.interFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

@Composable
fun PropertyItemView(
    propertyItem: Property,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { propertyItem.propertyImages.size })
    val coroutineScope = rememberCoroutineScope()
    var isHovered by remember { mutableStateOf(false) }
    val uriList = propertyItem.propertyImages.map { Uri.parse(it) }

    LaunchedEffect(isHovered) {
        if (isHovered) {
            while (true) {
                yield()
                delay(2000)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % pagerState.pageCount,
                    animationSpec = tween(durationMillis = 500, easing = EaseInOut)
                )
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)  // Reduced height
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
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    AsyncImage(
                        model = uriList[page],
                        contentDescription = "Property Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    (pagerState.currentPage - 1 + uriList.size) % uriList.size
                                )
                            }
                        }
                    ) {
                        Icon(
                            painterResource(id =R.drawable.baseline_arrow_back_ios_24 ),
                            contentDescription = "Previous",
                            tint = Color.Black
                        )
                    }
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    (pagerState.currentPage + 1) % uriList.size
                                )
                            }
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                            contentDescription = "Next",
                            tint = Color.Black
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)  // Reduced padding
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₹${propertyItem.rent}/month",
                        style = MaterialTheme.typography.titleMedium,  // Reduced font size
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {  // Reduced size
                            Icon(Icons.Filled.Share, contentDescription = "Share")
                        }
                        Spacer(Modifier.width(MaterialTheme.dimens.small1))
                        IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {  // Reduced size
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_favorite_border_24),
                                contentDescription = "Favorite"
                            )
                        }
                        Spacer(Modifier.width(MaterialTheme.dimens.small1))
                        IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {  // Reduced size
                            Icon(Icons.Outlined.LocationOn, contentDescription = "Location")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column{
                        Text(
                            text = "${propertyItem.sharingType} Sharing ",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "${propertyItem.size} ",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Owner: ${propertyItem.owner}",
                            style = MaterialTheme.typography.bodySmall,  // Reduced font size
                            textDecoration = TextDecoration.Underline
                        )
                    }
                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)  // Reduced padding
                    ) {
                        Text("Book Now", style = MaterialTheme.typography.labelMedium)  // Reduced font size
                    }
                }


            }
        }
    }
}

@Composable
private fun SlidingImages(
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    imageUris: List<Uri>
) {
    val iconSize: Dp = MaterialTheme.dimens.small3
    val imageSize: Dp = MaterialTheme.dimens.large + 30.dp

    Icon(
        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
        contentDescription = "Previous Image",
        tint = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .size(iconSize)
            .clickable {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage - 1 + imageUris.size) % imageUris.size
                    )
                }
            }
    )

    Box(
        modifier = Modifier
            .size(width = imageSize, height = imageSize)
    ) {
        HorizontalPager(state = pagerState) { page ->
            val painter: Painter = rememberAsyncImagePainter(model = imageUris[page])
            Image(
                painter = painter,
                contentDescription = "Room Image",
                modifier = Modifier
                    .size(width = imageSize, height = imageSize),
                contentScale = ContentScale.Crop
            )
        }
    }

    Icon(
        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
        contentDescription = "Next Image",
        tint = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .size(iconSize)
            .clickable {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % imageUris.size
                    )
                }
            }
    )
    
}

@Preview(showBackground = true)
@Composable
fun PropertyItemViewPreview() {
    val sampleProperty = Property(
        owner = "John Doe",
        propertyName = "Green View Apartment",
        ownerId = "OWN12345",
        ownerMobNo = "+91 9876543210",
        ownerEmail = "johndoe@example.com",
        rent = "15000",
        sharingType = "Double",
        size = "1200 sqft",
        address = "123 Green View Street",
        city = "Mumbai",
        locality = "Andheri West",
        landmark = "Near Green Park",
        floor = "3rd",
        createdAt = "2024-09-10",
        updatedAt = "2024-09-17",
        housetype = "Apartment",
        roomtype = "2BHK",
        availablefor = "Family",
        deposit = "2 Months",
        maintenance = "Included",
        electricbill = "Separate",
        parking = "Car",
        nonveg = "Allowed",
        balcony = "Yes",
        bathroom = 2,
        toilet = 2,
        wifi = "Available",
        amenities = listOf("AC", "TV", "Fridge", "Washing Machine"),
        propertyImages = listOf("image1.jpg", "image2.jpg", "image3.jpg")
    )

    PropertyItemView(propertyItem =sampleProperty )
}


