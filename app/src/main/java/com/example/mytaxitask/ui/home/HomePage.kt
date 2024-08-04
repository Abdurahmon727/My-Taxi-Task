package com.example.mytaxitask.ui.home

import LocationService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.graphics.drawable.toBitmap
import com.example.mytaxitask.R
import com.example.mytaxitask.core.base.AppScreen
import com.example.mytaxitask.core.composables.RoundedButton
import com.example.mytaxitask.core.constants.AppConstants
import com.example.mytaxitask.core.extensions.Width
import com.example.mytaxitask.core.extensions.advancedShadow
import com.example.mytaxitask.ui.theme.shadowColor
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar


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

        val permissionRequest =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    if (!permissions.values.all { it }) {
                        //handle permission denied
                    } else {
                        relaunch = !relaunch
                    }
                })

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {

//
            MapView(
                point = point, modifier = Modifier.fillMaxSize()
            )

            Row(modifier = Modifier.padding(16.dp)) {
                RoundedButton(onClick = {
                    //todo
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_hamburger),
                        contentDescription = ""
                    )
                }
                12.Width()

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
//                            .shadow(elevation = 5.dp,
//                                shape = RoundedCornerShape(14.dp)
//                            )
                        .advancedShadow(
                            color = shadowColor,
                            offsetX = 0.dp,
                            offsetY = 8.dp,
                            blurRadius = 11.dp,
                        )
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {

                }


                12.Width()


                RoundedButton(
                    fillColor = MaterialTheme.colorScheme.secondary
                ) {
                    Text(
                        text = "95", style = MaterialTheme.typography.headlineMedium,
                    )
                }
            }
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
        AndroidView(factory = {
            MapView(it).also { mapView ->
                mapView.mapboxMap.loadStyleUri(AppConstants.MAPURL)

                mapView.scalebar.enabled = false
                mapView.logo.updateSettings { enabled = false }
                mapView.attribution.updateSettings {
                    enabled = false
                }
                val annotationApi = mapView.annotations
                pointAnnotationManager = annotationApi.createPointAnnotationManager()
            }
        }, update = { mapView ->
            if (point != null) {
                pointAnnotationManager?.let {
                    it.deleteAll()
                    val pointAnnotationOptions =
                        PointAnnotationOptions().withPoint(point).withIconImage(marker)

                    it.create(pointAnnotationOptions)
                    mapView.mapboxMap.flyTo(
                        CameraOptions.Builder().zoom(16.0).center(point).build()
                    )
                }
            }
            NoOpUpdate
        }, modifier = modifier
        )
    }
}