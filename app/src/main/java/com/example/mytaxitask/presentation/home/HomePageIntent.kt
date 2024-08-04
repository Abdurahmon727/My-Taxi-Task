package com.example.mytaxitask.presentation.home

import android.graphics.Bitmap
import com.mapbox.maps.MapView


sealed class HomePageIntent {
    data class Init(val mapView: MapView, val locationLogo: Bitmap) : HomePageIntent()
    data object ToggleDriverStatus : HomePageIntent()
    data object ShowMyLocation : HomePageIntent()
    data object MapZoomIn : HomePageIntent()
    data object MapZoomOut : HomePageIntent()

}