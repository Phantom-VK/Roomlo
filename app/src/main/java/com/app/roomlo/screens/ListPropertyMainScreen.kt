import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.roomlo.dataclasses.Property
import com.app.roomlo.navigation.Screen


@Composable
fun ListPropertyScaffoldScreen(navController: NavController) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.ListPropertyAddressView) }
    val property = remember { mutableStateOf(Property()) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(navController)
            Divider()
            ScreenTitle(currentScreen)
            ScreenTabs(currentScreen) { newScreen ->
                currentScreen = newScreen
            }
            LinearProgressIndicator(
                progress = when (currentScreen) {
                    Screen.ListPropertyAddressView -> 0.33f
                    Screen.ListPropertyDetailsView -> 0.66f
                    Screen.ListPropertyImagesView -> 1f
                    else -> 0.0f
                },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary,
            )

            when (currentScreen) {
                Screen.ListPropertyAddressView -> AddressScreen(navController, property.value) {
                    currentScreen = Screen.ListPropertyDetailsView
                }
                Screen.ListPropertyDetailsView -> PropertyDetailsFormScreen(property.value) {
                    currentScreen = Screen.ListPropertyImagesView
                }
                Screen.ListPropertyImagesView -> PropertyImagesUploadView(property.value) {
                    // TODO: Handle final submission
                }

                else -> AddressScreen(navController = navController, property = property.value) {

                }
            }
        }
    }
}

@Composable
fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go Back",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Text(
            text = "List Property",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun ScreenTitle(currentScreen: Screen) {
    Text(
        text = "Add ${currentScreen.route.capitalize()}",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun ScreenTabs(currentScreen: Screen, onTabSelected: (Screen) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ScreenTab("Address", currentScreen == Screen.ListPropertyAddressView) {
            onTabSelected(Screen.ListPropertyAddressView)
        }
        ScreenTab("Details", currentScreen == Screen.ListPropertyDetailsView) {
            onTabSelected(Screen.ListPropertyDetailsView)
        }
        ScreenTab("Images", currentScreen == Screen.ListPropertyImagesView) {
            onTabSelected(Screen.ListPropertyImagesView)
        }
    }
}

@Composable
fun ScreenTab(label: String, isSelected: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = label,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun AddressScreen(navController: NavController, property: Property, onNext: () -> Unit) {
    var city by remember { mutableStateOf(property.city) }
    var locality by remember { mutableStateOf(property.locality) }
    var landmark by remember { mutableStateOf(property.landmark) }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AddressTextField(value = city, placeholder = "City") { city = it }
        AddressTextField(value = locality, placeholder = "Locality") { locality = it }
        AddressTextField(value = landmark, placeholder = "Landmark/Street") { landmark = it }
        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        Text(
            text = "Mark your Property on Map",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Set property location by using search box and move the map",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search location") },
            modifier = Modifier.fillMaxWidth()
        )

        // TODO: Add map component here

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                property.apply {
                    this.city = city
                    this.locality = locality
                    this.landmark = landmark
                    address = "$city, $locality, $landmark"
                }
                onNext()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save & Continue")
        }
    }
}

@Composable
fun PropertyDetailsFormScreen(property: Property, onNext: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = property.rent,
                    onValueChange = { property.rent = it },
                    label = { Text("Room Price") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                OutlinedTextField(
                    value = property.size,
                    onValueChange = { property.size = it },
                    label = { Text("Room Size") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item { SectionWithOptions("Floor:", listOf("1st", "2nd", "Custom")) { property.floor = it } }
        item { SectionWithOptions("Deposit:", listOf("1 Month", "2 Months", "Custom")) { property.deposit = it } }
        item { SectionWithOptions("Maintenance:", listOf("Included", "Custom")) { property.maintenance = it } }
        item { SectionWithOptions("Electricity Bill:", listOf("Included", "Separate")) { property.electricbill = it } }
        item { SectionWithOptions("Parking:", listOf("Bike", "Car", "Both", "None")) { property.parking = it } }
        item { SectionWithOptions("Non-veg:", listOf("Yes", "No")) { property.nonveg = it } }
        item { SectionWithOptions("Wifi:", listOf("Yes", "No")) { property.wifi = it } }
        item {
            SectionWithOptions("Facilities:", listOf("Bathroom", "Toilet", "Balcony", "Custom")) { facility ->
                property.Amenities += facility
            }
        }

        item {
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Save and Continue")
            }
        }
    }
}

@Composable
fun SectionWithOptions(label: String, options: List<String>, onOptionSelected: (String) -> Unit) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(options) { option ->
            if (option == "Custom") {
                CustomOptionWithTextField(textFieldPlaceholder = "Custom", onValueChange = onOptionSelected)
            } else {
                OptionButton(text = option, onClick = { onOptionSelected(option) })
            }
        }
    }
}

@Composable
fun OptionButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text, fontSize = 14.sp)
    }
}

@Composable
fun CustomOptionWithTextField(textFieldPlaceholder: String, onValueChange: (String) -> Unit) {
    var value by remember { mutableStateOf("") }
    OutlinedTextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        label = { Text(textFieldPlaceholder, fontSize = 14.sp) },
        modifier = Modifier
            .width(120.dp)
            .height(56.dp)
    )
}

@Composable
fun PropertyImagesUploadView(property: Property, onSubmit: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Upload Photos & Videos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Upload images and videos to help buyers visualize the property.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // TODO: Add image and video upload components

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Listing")
        }
    }
}

@Composable
fun AddressTextField(value: String, placeholder: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ListPropertyScaffoldScreenPreview() {
    ListPropertyScaffoldScreen(navController = rememberNavController())
}
