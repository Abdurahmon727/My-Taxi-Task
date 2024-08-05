package com.example.mytaxitask.presentation.home

import android.content.Context
import com.mapbox.maps.MapView


sealed class HomePageIntent {
    data class Init(val mapView: MapView, val context: Context) : HomePageIntent()
    data object ToggleDriverStatus : HomePageIntent()
    data class ShowMyLocation(val context: Context) : HomePageIntent()
    data object MapZoomIn : HomePageIntent()
    data object MapZoomOut : HomePageIntent()

}