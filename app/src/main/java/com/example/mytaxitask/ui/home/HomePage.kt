package com.example.mytaxitask.ui.home

import LocationService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.graphics.drawable.toBitmap
import com.example.mytaxitask.R
import com.example.mytaxitask.core.base.AppScreen
import com.example.mytaxitask.core.constants.AppConstants
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager


class HomePage : AppScreen {
    @Composable
    override fun Content() {


        var point: Point? by remember {
            mutableStateOf(null)
        }
        var relaunch by remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current

        val permissionRequest = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                if (!permissions.values.all { it }) {
                    //handle permission denied
                } else {
                    relaunch = !relaunch
                }
            }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

//
            MapView(
                point = point,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        LaunchedEffect(key1 = relaunch) {
            try {

                val location = LocationService().getCurrentLocation(context)
                point = Point.fromLngLat(location.longitude, location.latitude)

            } catch (e: LocationService.LocationServiceException) {
                when (e) {
                    is LocationService.LocationServiceException.LocationDisabledException -> {
                        //handle location disabled, show dialog or a snack-bar to enable location
                    }

                    is LocationService.LocationServiceException.MissingPermissionException -> {
                        permissionRequest.launch(
                            arrayOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }

                    is LocationService.LocationServiceException.NoInternetException -> {
                        //handle no network enabled, show dialog or a snack-bar to enable network
                    }

                    is LocationService.LocationServiceException.UnknownException -> {
                        //handle unknown exception
                    }

                    else -> {}
                }
            }
        }
    }

    @Composable
    fun MapView(
        modifier: Modifier = Modifier,
        point: Point?,
    ) {
        val context = LocalContext.current
        val marker = remember(context) {
            context.getDrawable(R.drawable.ic_car)!!.toBitmap()
        }
        var pointAnnotationManager: PointAnnotationManager? by remember {
            mutableStateOf(null)
        }
        AndroidView(
            factory = {
                MapView(it).also { mapView ->
                    mapView.mapboxMap.loadStyleUri(AppConstants.MAPURL)
                    val annotationApi = mapView.annotations
                    pointAnnotationManager = annotationApi.createPointAnnotationManager()
                }
            },
            update = { mapView ->
                if (point != null) {
                    pointAnnotationManager?.let {
                        it.deleteAll()
                        val pointAnnotationOptions = PointAnnotationOptions()
                            .withPoint(point)
                            .withIconImage(marker)

                        it.create(pointAnnotationOptions)
                        mapView.mapboxMap
                            .flyTo(CameraOptions.Builder().zoom(16.0).center(point).build())
                    }
                }
                NoOpUpdate
            },
            modifier = modifier
        )
    }
}