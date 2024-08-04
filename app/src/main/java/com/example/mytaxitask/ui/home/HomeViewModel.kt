package com.example.mytaxitask.ui.home

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class HomeViewModel : ViewModel() {
    val state = MutableStateFlow(HomeUiState())


    fun onIntentDispatched(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Init ->{

            }
            is HomeIntent.NavigateMyLocation -> showMyLocation()
            is HomeIntent.ToggleDriverStatus -> {
                state.update { it.copy(isDriverActive = !state.value.isDriverActive) }
            }
        }
    }

    private fun init() {

    }
    private fun showMyLocation() {
        state.value.mapView.let {

        }
    }

}

data class HomeUiState(
    val mapView: MapView? = null,
    val currentPoint: Point? = null,
    val isDriverActive: Boolean = false,
)

sealed class HomeIntent {
    data class Init(val mapView: MapView, val locationLogo: Bitmap) : HomeIntent()

    data object ToggleDriverStatus : HomeIntent()

    data object NavigateMyLocation : HomeIntent()
}

