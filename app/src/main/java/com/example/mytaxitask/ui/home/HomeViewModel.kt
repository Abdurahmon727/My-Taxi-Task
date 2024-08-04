package com.example.mytaxitask.ui.home

import androidx.lifecycle.ViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView


class HomeViewModel : ViewModel() {

}

data class HomeUiState(
    val mapView: MapView?,
    val currentPoint: Point?,
)

sealed class HomeIntent {
    data object GetLocation : HomeIntent()


}

