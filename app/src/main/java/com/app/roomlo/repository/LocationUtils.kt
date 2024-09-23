package com.app.roomlo.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Looper
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
class LocationUtils@Inject constructor(private val context: Context, private val viewModel: LocationViewModel) {

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