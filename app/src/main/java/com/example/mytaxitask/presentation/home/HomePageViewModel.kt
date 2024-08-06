package com.example.mytaxitask.presentation.home

import android.graphics.Bitmap
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytaxitask.domain.model.Either
import com.example.mytaxitask.service.LocationService
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomePageViewModel(
    private val locationService: LocationService,
) : ViewModel() {
    val state = MutableStateFlow(HomePageState())

    private var mapBox: MapboxMap? = null
    private var pointAnnotationManager: PointAnnotationManager? = null


    fun onIntentDispatched(intent: HomePageIntent) {
        when (intent) {
            is HomePageIntent.InitMap -> initMap(intent.mapView)
            is HomePageIntent.ShowMyLocation -> showMyLocation(intent.marker)
            is HomePageIntent.MapZoomIn -> mapZoomIn()
            is HomePageIntent.MapZoomOut -> mapZoomOut()
            is HomePageIntent.ToggleDriverStatus -> {
                state.update { it.copy(isDriverActive = !state.value.isDriverActive) }
            }
        }
    }


    private fun initMap(mapView: MapView) {
        mapBox = mapView.mapboxMap
        pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
    }

    private fun showMyLocation(marker:Bitmap) {
        viewModelScope.launch {
            mapBox?.let { mapBox ->
                val currentLocation = locationService.getCurrentLocation()
                if (currentLocation is Either.Left) {
                    return@launch
                }
                val point = (currentLocation as Either.Right<Location>).data
                val currentPoint = Point.fromLngLat(point.longitude, point.latitude)

                pointAnnotationManager?.let {
                    it.deleteAll()

                    val pointAnnotationOptions =
                        PointAnnotationOptions().withPoint(currentPoint).withIconImage(marker)

                    it.create(pointAnnotationOptions)
                    mapBox.flyTo(
                        CameraOptions.Builder().zoom(16.0).center(currentPoint).build()
                    )
                }
            }
        }
    }

    private fun mapZoomIn() {
        mapBox?.let { mapBox ->
            mapBox.flyTo(
                CameraOptions.Builder().zoom(mapBox.cameraState.zoom + 1).build()
            )
        }
    }

    private fun mapZoomOut() {
        mapBox?.let { mapBox ->
            mapBox.flyTo(
                CameraOptions.Builder().zoom(mapBox.cameraState.zoom - 1).build()
            )
        }
    }
}


