@Composable
fun MapScreen(preferenceHelper: PreferenceHelper) {

    val location = LatLng(preferenceHelper.latitude?.toDouble() ?: 0.0, preferenceHelper.longitude?.toDouble() ?: 0.0)



    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
    }

    val isDarkTheme = isSystemInDarkTheme()
    val mapStyle = if (isDarkTheme) R.raw.map_dark_style else R.raw.map_light_style

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)  // Fixed height for the map
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp)),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                    LocalContext.current, mapStyle
                )
            )
        ) {
            Marker(state = MarkerState(position = location))
            Circle(
                center = location,
                radius = 200.0,  // Radius in meters
                strokeColor = MaterialTheme.colorScheme.primary,
                strokeWidth = 2f,
                fillColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}