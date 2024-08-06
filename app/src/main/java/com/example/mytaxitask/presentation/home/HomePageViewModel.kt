package com.example.mytaxitask.presentation.home

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytaxitask.R
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
            is HomePageIntent.Init -> init(intent.mapView, intent.context)
            is HomePageIntent.ShowMyLocation -> showMyLocation(intent.context)
            is HomePageIntent.MapZoomIn -> mapZoomIn()
            is HomePageIntent.MapZoomOut -> mapZoomOut()
            is HomePageIntent.ToggleDriverStatus -> {
                state.update { it.copy(isDriverActive = !state.value.isDriverActive) }
            }
        }
    }


    private fun init(mapView: MapView, context: Context) {
        mapBox = mapView.mapboxMap
        viewModelScope.launch {
            val location = locationService.getCurrentLocation(context)
            val point = Point.fromLngLat(location.longitude, location.latitude)
            pointAnnotationManager = mapView.annotations.createPointAnnotationManager()

            pointAnnotationManager?.let {
                it.deleteAll()

                val marker = context.getDrawable(R.drawable.ic_car)!!.toBitmap()
                val pointAnnotationOptions =
                    PointAnnotationOptions().withPoint(point).withIconImage(marker)

                it.create(pointAnnotationOptions)
                mapView.mapboxMap.flyTo(
                    CameraOptions.Builder().zoom(16.0).center(point).build()
                )
            }
        }
    }

    private fun showMyLocation(context: Context) {
        viewModelScope.launch {
            mapBox?.let { mapBox ->
                val currentLocation = locationService.getCurrentLocation(context)
                val currentPoint =
                    Point.fromLngLat(currentLocation.longitude, currentLocation.latitude)

                pointAnnotationManager?.let {
                    it.deleteAll()

                    val marker = context.getDrawable(R.drawable.ic_car)!!.toBitmap()
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


