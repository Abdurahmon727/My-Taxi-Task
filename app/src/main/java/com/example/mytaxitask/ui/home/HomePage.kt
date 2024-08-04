package com.example.mytaxitask.ui.home

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.mytaxitask.core.base.AppScreen
import com.example.mytaxitask.ui.home.components.HomeActionButtons
import com.example.mytaxitask.ui.home.components.HomeMapView
import com.example.mytaxitask.ui.home.components.HomeTopBar



class HomePage(val viewModel: HomeViewModel) : AppScreen {
    @Composable
    override fun Content() {
        val intent = viewModel::onIntentDispatched

        val permissionRequest =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    Log.i("LOCATION PERMISSION RESULT","$permissions")
                    if (!permissions.values.all { it }) {
                        //handle permission denied
                    } else {
//                        relaunch = true
                    }
                })

        val state = viewModel.state.collectAsState().value

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            HomeMapView(
                modifier = Modifier.fillMaxSize(),
                intent = intent,
            )

            HomeTopBar(isDriverActive = state.isDriverActive,
                onChangeStatus = {
                    intent.invoke(HomeIntent.ToggleDriverStatus)
                })

            HomeActionButtons(showMe = {
//                coroutine.launch {
//                    val location = LocationService().getCurrentLocation(context)
//                    val point = Point.fromLngLat(location.longitude, location.latitude)
//                    val pointAnnotationManager =
//                        mapView.value?.annotations?.createPointAnnotationManager()
//
//                    pointAnnotationManager?.let {
//                        it.deleteAll()
//
//                        val pointAnnotationOptions =
//                            PointAnnotationOptions().withPoint(point).withIconImage(marker)
//
//                        it.create(pointAnnotationOptions)
//                        mapView.value?.mapboxMap?.flyTo(
//                            CameraOptions.Builder().zoom(16.0).center(point).build()
//                        )
//                    }
//                }
//                mapView.value
            })
        }

//        LaunchedEffect(key1 = relaunch) {
//            try {
//                val location = LocationService().getCurrentLocation(context)
//                    val point = Point.fromLngLat(location.longitude, location.latitude)
//
//            } catch (e: LocationService.LocationServiceException) {
//                when (e) {
//                    is LocationService.LocationServiceException.LocationDisabledException -> {
//                        //handle location disabled, show dialog or a snack-bar to enable location
//                    }
//
//                    is LocationService.LocationServiceException.MissingPermissionException -> {
//                        permissionRequest.launch(
//                            arrayOf(
//                                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                                android.Manifest.permission.ACCESS_COARSE_LOCATION
//                            )
//                        )
//                    }
//
//                    is LocationService.LocationServiceException.NoInternetException -> {
//                        //handle no network enabled, show dialog or a snack-bar to enable network
//                    }
//
//                    is LocationService.LocationServiceException.UnknownException -> {
//                        //handle unknown exception
//                    }
//
//                    else -> {}
//                }
//            }
//        }
    }


}