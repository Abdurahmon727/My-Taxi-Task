package com.example.mytaxitask.presentation.home

import android.graphics.Bitmap
import com.mapbox.maps.MapView


sealed class HomePageIntent {
    data class InitMap(val mapView: MapView) : HomePageIntent()
    data object ToggleDriverStatus : HomePageIntent()
    data class ShowMyLocation(val marker: Bitmap) : HomePageIntent()
    data object MapZoomIn : HomePageIntent()
    data object MapZoomOut : HomePageIntent()

}