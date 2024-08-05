package com.example.mytaxitask.presentation.home

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mytaxitask.core.base.AppScreen
import com.example.mytaxitask.core.status.BottomSheetStatus
import com.example.mytaxitask.presentation.home.components.HomeActionButtons
import com.example.mytaxitask.presentation.home.components.HomeBottomSheetContent
import com.example.mytaxitask.presentation.home.components.HomeMapView
import com.example.mytaxitask.presentation.home.components.HomeTopBar
import io.morfly.compose.bottomsheet.material3.BottomSheetScaffold
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState

class HomePage(val viewModel: HomePageViewModel) : AppScreen {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                Log.i("LOCATION PERMISSION RESULT", "$permissions")
                if (!permissions.values.all { it }) {
                    //handle permission denied
                } else {
//                        relaunch = true
                }
            },
        )
        val uiState = viewModel.state.collectAsState().value
        val intent = viewModel::onIntentDispatched


        val sheetState = rememberBottomSheetState(
            initialValue = BottomSheetStatus.Collapsed,
            defineValues = {
                BottomSheetStatus.Collapsed at height(150.dp)
                BottomSheetStatus.Expanded at contentHeight
            }
        )

        val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                HomeBottomSheetContent()
            },
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                HomeMapView(
                    modifier = Modifier.fillMaxSize(),
                    intent = intent,
                )

                HomeTopBar(
                    isDriverActive = uiState.isDriverActive,
                    onChangeStatus = {
                        intent.invoke(HomePageIntent.ToggleDriverStatus)
                    },
                )

                HomeActionButtons(
                    visible = sheetState.targetValue == BottomSheetStatus.Collapsed,
                    intent = intent,
//                    showMe = {

//                coroutine.launch {
//                    val location = com.example.mytaxitask.service.LocationService().getCurrentLocation(context)
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
//                    }
                )


            }
        }

//        LaunchedEffect(key1 = relaunch) {
//            try {
//                val location = com.example.mytaxitask.service.LocationService().getCurrentLocation(context)
//                    val point = Point.fromLngLat(location.longitude, location.latitude)
//
//            } catch (e: com.example.mytaxitask.service.LocationService.LocationServiceException) {
//                when (e) {
//                    is com.example.mytaxitask.service.LocationService.LocationServiceException.LocationDisabledException -> {
//                        //handle location disabled, show dialog or a snack-bar to enable location
//                    }
//
//                    is com.example.mytaxitask.service.LocationService.LocationServiceException.MissingPermissionException -> {
//                        permissionRequest.launch(
//                            arrayOf(
//                                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                                android.Manifest.permission.ACCESS_COARSE_LOCATION
//                            )
//                        )
//                    }
//
//                    is com.example.mytaxitask.service.LocationService.LocationServiceException.NoInternetException -> {
//                        //handle no network enabled, show dialog or a snack-bar to enable network
//                    }
//
//                    is com.example.mytaxitask.service.LocationService.LocationServiceException.UnknownException -> {
//                        //handle unknown exception
//                    }
//
//                    else -> {}
//                }
//            }
//        }
    }


}