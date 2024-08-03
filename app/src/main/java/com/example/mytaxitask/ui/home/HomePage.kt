package com.example.mytaxitask.ui.home

import LocationService
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.graphics.drawable.toBitmap
import com.example.mytaxitask.R
import com.example.mytaxitask.core.AppScreen
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import kotlinx.coroutines.launch


class HomePage : AppScreen {
    @OptIn(MapboxExperimental::class)
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
                }
                else {
                    relaunch = !relaunch
                }
            }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
//            MapboxMap(
//                Modifier.fillMaxSize(),
//                mapViewportState = MapViewportState().apply {
//                    setCameraOptions {
//                        zoom(2.0)
//                        center(Point.fromLngLat(-98.0, 39.5))
//                        pitch(0.0)
//                        bearing(0.0)
//                    }
//                    setCameraOptions { CameraOptions. }
//
//                },
//            )

            MapBoxMap(
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
    fun MapBoxMap(
        modifier: Modifier = Modifier,
        point: Point?,
    ) {
        val context = LocalContext.current
        val marker = remember(context) {
            context.getDrawable(R.drawable.ic_launcher_foreground)!!.toBitmap()
        }
        var pointAnnotationManager: PointAnnotationManager? by remember {
            mutableStateOf(null)
        }
        AndroidView(
            factory = {
                MapView(it).also { mapView ->
                    mapView.getMapboxMap().loadStyleUri("mapbox://styles/abdurahmon727/clzdt32n200c601qybtb6b8yf")
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
                        mapView.getMapboxMap()
                            .flyTo(CameraOptions.Builder().zoom(16.0).center(point).build())
                    }
                }
                NoOpUpdate
            },
            modifier = modifier
        )
    }
}