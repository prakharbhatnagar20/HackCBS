package com.example.hackcbs.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.hackcbs.data.LocationData
import androidx.compose.runtime.State
import android.location.Location
import androidx.lifecycle.viewModelScope
import com.example.hackcbs.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LocationViewModel: ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    fun updateLocation(newLocation: LocationData){
        _location.value = newLocation
    }

    fun sendLocationData(location: Location) {
        val locationEntry = LocationData(
            id = System.currentTimeMillis(),
            latitude = location.latitude,
            longitude = location.longitude
        )

        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}