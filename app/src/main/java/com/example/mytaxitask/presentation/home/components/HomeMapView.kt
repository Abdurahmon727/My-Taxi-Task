package com.example.mytaxitask.presentation.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.example.mytaxitask.R
import com.example.mytaxitask.core.constants.AppConstants
import com.example.mytaxitask.presentation.home.HomeIntent
import com.example.mytaxitask.presentation.home.HomePageIntent
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar

@Composable
fun HomeMapView(
    modifier: Modifier = Modifier,
    intent: (HomeIntent) -> Unit,
) {
    val context = LocalContext.current
    val marker = remember(context) {
        context.getDrawable(R.drawable.ic_car)!!.toBitmap()
    }
    AndroidView(
        modifier = modifier,
        factory = {
            MapView(it).also { mapView ->
                mapView.mapboxMap.loadStyleUri(AppConstants.MAPURL)
                mapView.scalebar.enabled = false
                mapView.logo.updateSettings { enabled = false }
                mapView.attribution.updateSettings {
                    enabled = false
                }
                intent.invoke(HomePageIntent.Init(mapView, marker))
            }
        },
//        update = { mapView ->
//            if (point != null) {
//                pointAnnotationManager?.let {
//                    it.deleteAll()
//                    val pointAnnotationOptions =
//                        PointAnnotationOptions().withPoint(point).withIconImage(marker)
//
//                    it.create(pointAnnotationOptions)
//                    mapView.mapboxMap.flyTo(
//                        CameraOptions.Builder().zoom(16.0).center(point).build()
//                    )
//                }
//            }
//            NoOpUpdate
//        },
    )
}