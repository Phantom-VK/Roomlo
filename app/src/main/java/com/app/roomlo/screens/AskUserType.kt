package com.app.roomlo.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.roomlo.R
import com.app.roomlo.dataclasses.UserType
import com.app.roomlo.navigation.Screen

@Preview
@Composable
fun AskUserType(
	navController: NavController = rememberNavController()
) {
	var userType by remember{ mutableStateOf("") }
	Box(modifier = Modifier.fillMaxSize()) {
		Image(
			painter = painterResource(id = R.drawable.ask_user_type_bg),
			contentDescription = "",
			contentScale = ContentScale.Crop,
			modifier = Modifier.fillMaxSize()
		)
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			MyTopAppBar{navController.navigate(Screen.SignInScreen.route)}

			Spacer(modifier = Modifier.height(149.dp))

			Text(
				text = "Get Rooms\nAnywhere In India.",
				color = Color.White,
				fontWeight = FontWeight.ExtraBold, fontSize = 45.sp, modifier = Modifier
					.padding(start = 5.dp)
					.fillMaxWidth()
			)

			Spacer(
				modifier = Modifier
					.height(73.dp)
					.fillMaxWidth()
			)
			CenterBlackBox {
				val route="${Screen.SignUpScreen.route}/$it"
				navController.navigate(route)}

			Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start) {
				RadialWhite(modifier = Modifier.offset(x = 170.dp, y = 72.dp))
			}
		}
	}
}

@Composable
private fun CenterBlackBox(onClick: (userType:String) -> Unit) {
	Box(
		modifier = Modifier
			.width(270.dp)
			.height(204.dp)
			.background(Color(0xFF101820).copy(0.9f))
			.padding(horizontal = 36.dp, vertical = 26.dp),
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.SpaceEvenly
		) {

			Text(
				text = "Who are you?",
				color = Color.White,
				fontWeight = FontWeight.ExtraBold, fontSize = 20.sp,
				modifier = Modifier.padding(bottom = 8.dp)
			)
			OwnerTenantButton(
				primaryText = "Owner",
				secondaryText = "Rent Room",
				onClick = { onClick(UserType.Owner.type) })
			OwnerTenantButton(
				primaryText = "Tenant",
				secondaryText = "Looking for a room",
				onClick = { onClick(UserType.Tenant.type) })
		}
	}
}

@Composable
private fun MyTopAppBar(loginOnClickListener:()->Unit) {
	TopAppBar(title =
	{
		Row(
			horizontalArrangement = Arrangement.SpaceAround,
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.fillMaxWidth()
		) {
			Spacer(modifier = Modifier.fillMaxHeight())
			Row(verticalAlignment = Alignment.CenterVertically) {
				Image(
					painter = painterResource(id = R.drawable.keyhole),
					contentDescription = "", modifier = Modifier
						.height(24.dp)
						.width(12.dp)
				)
				Text("Roomlo", fontSize = 24.sp)
			}
			Box(
				modifier = Modifier
					.width(46.dp)
					.height(25.dp)
					.background(
						color = Color.Black,
						shape = RoundedCornerShape((18.5).dp)
					)
					.clickable { loginOnClickListener() }
				, contentAlignment = Alignment.Center
			) {
				Text(text = "Login", color = Color.White, fontSize = 12.sp)
			}
		}
	}, backgroundColor = Color.Transparent, navigationIcon = {
		IconButton(onClick = {
			//open drawer
		}) {
			Icon(imageVector = Icons.Default.Menu, contentDescription = "")
		}
	}
	)
}

@Composable
private fun OwnerTenantButton(
	primaryText: String = "Owner",
	secondaryText: String = "Rent Room",
	onClick: () -> Unit
) {
	Box(
		modifier = Modifier
			.padding(vertical = 4.dp)
			.fillMaxWidth()
			.height(50.dp)
			.clip(RoundedCornerShape(53.dp))
			.background(Color.White)
			.clickable { onClick() }
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
			modifier = Modifier.fillMaxSize()
		) {
			Text(text = primaryText, fontSize = 18.sp)
			Text(
				text = "($secondaryText)",
				fontStyle = FontStyle.Italic,
				color = Color(0XFF7A7979),
				fontSize = 9.sp
			)
		}
	}

}


@Preview
@Composable
private fun RadialWhite(modifier: Modifier = Modifier) {
	val ellipseShape = GenericShape { rectSize, _ ->
		addOval(
			oval = Rect(0f, 0f, rectSize.width, rectSize.height),
			direction = Path.Direction.Clockwise

		)
	}
	Box(
		modifier = modifier
			.requiredWidth(291.dp)
			.requiredHeight(251.dp)
			.clipToBounds()
			.background(
				brush = Brush.radialGradient(colors = listOf(Color.White, Color.Transparent)),
				shape = ellipseShape
			), contentAlignment = Alignment.Center
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(text = "400+", fontWeight = FontWeight.Bold)
			Text(text = "Owners/Rooms", fontWeight = FontWeight.Bold)
			Text(text = "1000+", fontWeight = FontWeight.Bold)
			Text(text = "Tenants/Want Room", fontWeight = FontWeight.Bold)
		}
	}

}
