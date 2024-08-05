package com.example.mytaxitask.presentation.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mytaxitask.core.constants.AppConstants
import com.example.mytaxitask.presentation.home.HomePageIntent
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar

@Composable
fun HomeMapView(
    modifier: Modifier = Modifier,
    intent: (HomePageIntent) -> Unit,
) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = {
            MapView(it).also { mapView ->
                mapView.mapboxMap.loadStyle(AppConstants.MAPURL)
                mapView.scalebar.enabled = false
                mapView.compass.enabled = false
                mapView.logo.updateSettings { enabled = false }
                mapView.attribution.updateSettings {
                    enabled = false
                }
                intent.invoke(HomePageIntent.Init(mapView, context))
            }
        }
    )
}