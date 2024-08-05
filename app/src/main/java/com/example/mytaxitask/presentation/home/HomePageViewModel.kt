package com.example.mytaxitask.presentation.home

import LocationService
import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytaxitask.R
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomePageViewModel : ViewModel() {
    val state = MutableStateFlow(HomePageState())


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
        state.update { it.copy(mapView = mapView) }
        viewModelScope.launch {
            val location = LocationService().getCurrentLocation(context)
            val point = Point.fromLngLat(location.longitude, location.latitude)
            val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()

            pointAnnotationManager.let {
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
            state.value.mapView?.let { mapView ->

                val location = LocationService().getCurrentLocation(context)
                val point = Point.fromLngLat(location.longitude, location.latitude)
                val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()

                pointAnnotationManager.let {
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
    }

    private fun mapZoomIn() {
        state.value.mapView?.let { mapView ->
            mapView.mapboxMap.flyTo(
                CameraOptions.Builder().zoom(mapView.mapboxMap.cameraState.zoom + 1).build()
            )
        }
    }

    private fun mapZoomOut() {
        state.value.mapView?.let { mapView ->
            mapView.mapboxMap.flyTo(
                CameraOptions.Builder().zoom(mapView.mapboxMap.cameraState.zoom - 1).build()
            )
        }
    }


}


