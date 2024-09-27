package com.app.roomlo.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.app.roomlo.dataclasses.LocationData
import com.app.roomlo.viewmodels.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale
import javax.inject.Inject


/**
 * Utility class to handle location operations such as requesting location updates and reverse geocoding.
 *
 * @param context The context used to access system services and resources.
 */
class LocationUtils@Inject constructor(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Requests location updates and updates the provided ViewModel with the latest location.
     *
     * @param viewModel The ViewModel to update with the new location data.
     */
    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(viewModel: LocationViewModel) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    val location = LocationData(latitude = it.latitude, longitude = it.longitude)
                    viewModel.updateLocation(location)
                }
            }
        }

        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .build()

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun getCurrentLocation(
        viewModel: LocationViewModel,
        context: Context
    ): LocationData? {
        var currentLocation: LocationData? = null

        // Check permissions for fine and coarse location
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Request permissions here using ActivityCompat#requestPermissions if not granted
            return null
        }

        // Get last known location
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                // Update the current location and ViewModel
                currentLocation = LocationData(latitude = location.latitude, longitude = location.longitude)
                viewModel.updateCurrentLocation(currentLocation!!)
            }
        }.addOnFailureListener {
            // Handle failure to get location (e.g., location services turned off)
            Toast.makeText(context, "Failed to get location", Toast.LENGTH_SHORT).show()
            currentLocation = null
        }

        return currentLocation
    }


    /**
     * Converts latitude and longitude into a human-readable address.
     *
     * @param locationData The location data to convert.
     * @return The address as a string or a message indicating that the address was not found.
     */
    fun reverseGeocodeLocation(locationData: LocationData): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>? = geocoder.getFromLocation(locationData.latitude, locationData.longitude, 1)
        return addresses?.firstOrNull()?.getAddressLine(0) ?: "Address not found!"
    }
}