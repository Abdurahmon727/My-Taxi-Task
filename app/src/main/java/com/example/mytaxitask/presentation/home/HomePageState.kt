package com.example.mytaxitask.presentation.home

import com.mapbox.geojson.Point
import com.mapbox.maps.MapView


data class HomePageState(
    val mapView: MapView? = null,
    val currentPoint: Point? = null,
    val isDriverActive: Boolean = false,
)