package com.example.mytaxitask.presentation.home

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.example.mytaxitask.R
import com.example.mytaxitask.core.base.AppScreen
import com.example.mytaxitask.core.status.BottomSheetStatus
import com.example.mytaxitask.presentation.home.components.HomeActionButtons
import com.example.mytaxitask.presentation.home.components.HomeBottomSheetContent
import com.example.mytaxitask.presentation.home.components.HomeMapView
import com.example.mytaxitask.presentation.home.components.HomeTopBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import io.morfly.compose.bottomsheet.material3.BottomSheetScaffold
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState

class HomePage(val viewModel: HomePageViewModel) : AppScreen {
    @OptIn(
        ExperimentalMaterial3Api::class,
        ExperimentalFoundationApi::class,
        ExperimentalPermissionsApi::class
    )
    @Composable
    override fun Content() {
        val uiState = viewModel.state.collectAsState().value
        val intent = viewModel::onIntentDispatched
        val activity = LocalContext.current as? Activity


        val locationPermissions = rememberMultiplePermissionsState(permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ), onPermissionsResult = { permissions ->
            if (!permissions.values.all { it }) {
                Toast.makeText(activity, "Unable to access location", Toast.LENGTH_LONG).show()
            } else {
                val marker = activity?.getDrawable(R.drawable.ic_car)!!.toBitmap()
                intent.invoke(HomePageIntent.ShowMyLocation(marker))
            }
        })

        //1. when the app get launched for the first time
        LaunchedEffect(true) {
            locationPermissions.launchMultiplePermissionRequest()
        }


        val sheetState =
            rememberBottomSheetState(initialValue = BottomSheetStatus.Collapsed, defineValues = {
                BottomSheetStatus.Collapsed at height(150.dp)
                BottomSheetStatus.Expanded at contentHeight
            })
        val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                HomeBottomSheetContent()
            },
        ) {
            HomeMapView(modifier = Modifier.fillMaxSize(),
                onInitMap = { intent.invoke(HomePageIntent.InitMap(it)) })

            HomeTopBar(
                isDriverActive = uiState.isDriverActive,
                speed = uiState.speed,
                onChangeStatus = {
                    intent.invoke(HomePageIntent.ToggleDriverStatus)
                },
            )

            HomeActionButtons(
                visible = sheetState.targetValue == BottomSheetStatus.Collapsed, intent = intent
            )
        }
    }
}