package com.example.mytaxitask.ui.home

import LocationService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.mytaxitask.R
import com.example.mytaxitask.core.base.AppScreen
import com.example.mytaxitask.ui.home.components.HomeActionButtons
import com.example.mytaxitask.ui.home.components.HomeTopBar
import com.example.mytaxitask.ui.home.components.MapView
import com.example.mytaxitask.ui.theme.green
import com.example.mytaxitask.ui.theme.red
import com.mapbox.geojson.Point

data class DriverStatus(val statusTitle: Int, val color: Color)

class HomePage : AppScreen {
    @Composable
    override fun Content() {

        val driverStatuses = listOf(
            DriverStatus(R.string.busy, red), DriverStatus(R.string.active, green)
        )


        var point: Point? by remember {
            mutableStateOf(null)
        }
        var relaunch by remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        val selectedTabIndex = remember { mutableIntStateOf(0) }


        val permissionRequest =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    if (!permissions.values.all { it }) {
                        //handle permission denied
                    } else {
                        relaunch = true
                    }
                })

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            MapView(
                modifier = Modifier.fillMaxSize(),
                point = point,
            )

            HomeTopBar(selectedTabIndex = selectedTabIndex, driverStatuses = driverStatuses)

            HomeActionButtons()
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


}