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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.mytaxitask.R
import com.example.mytaxitask.core.base.AppScreen
import com.example.mytaxitask.core.composables.RoundedButton
import com.example.mytaxitask.core.extensions.Width
import com.example.mytaxitask.core.extensions.advancedShadow
import com.example.mytaxitask.ui.home.components.DriverStatusIndicator
import com.example.mytaxitask.ui.home.components.MapView
import com.example.mytaxitask.ui.home.components.myTabIndicatorOffset
import com.example.mytaxitask.ui.theme.shadowColor
import com.mapbox.geojson.Point


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
        var tabIndex by remember { mutableIntStateOf(0) }

        val indicator = @Composable { tabPositions: List<TabPosition> ->
//            TabRowDefaults.Indicator(
//                Modifier.tabIndicatorOffset(tabPositions[tabIndex])
//            )
            DriverStatusIndicator(
                Modifier.myTabIndicatorOffset(tabPositions[tabIndex]),
                tabIndex = tabIndex
            )
        }

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
                        .advancedShadow(
                            color = shadowColor,
                            offsetX = 0.dp,
                            offsetY = 8.dp,
                            blurRadius = 11.dp,
                        )
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    TabRow(
                        modifier = Modifier.fillMaxSize(),
                        selectedTabIndex = tabIndex, indicator = indicator,
                        divider = {},
                    ) {

                        Tab(
                            text = {
                            Text(
                                text = stringResource(id = R.string.busy),
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center
                            )
                        },
                            selected = tabIndex == 0,
                            onClick = { tabIndex = 0 }
                        )

                        Tab(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.active),
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center
                                )
                            },
                            selected = tabIndex == 1,
                            onClick = { tabIndex = 1 }
                        )


                    }

//                    Row(
//                        modifier = Modifier.padding(4.dp).fillMaxSize(),
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        Text(
//                            modifier = Modifier.weight(1f),
//                            text = stringResource(id = R.string.busy),
//                            textAlign = TextAlign.Center
//                        )
//                        Text(
//                            modifier = Modifier.weight(1f),
//                            text = stringResource(id = R.string.active),
//                            textAlign = TextAlign.Center
//                        )
//                    }
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


}