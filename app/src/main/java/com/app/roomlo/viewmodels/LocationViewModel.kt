package com.app.roomlo.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.app.roomlo.dataclasses.LocationData
import javax.inject.Singleton

/**
 * ViewModel to hold and manage location data.
 */
@Singleton
class LocationViewModel : ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> get() = _location

    private val _currentlocation = mutableStateOf<LocationData?>(null)
    val currentlocation: State<LocationData?> get() = _location

    /**
     * Updates the current location.
     *
     * @param newLocation The new location data to be updated.
     */
    fun updateLocation(newLocation: LocationData) {
        _location.value = newLocation
    }

    fun updateCurrentLocation(newLocation: LocationData) {
        _currentlocation.value = newLocation
    }

}