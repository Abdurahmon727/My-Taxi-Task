package com.example.mytaxitask.presentation.home

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
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
        val uiState = viewModel.state.collectAsState().value
        val intent = viewModel::onIntentDispatched

        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                if (!permissions.values.all { it }) {
                    Log.i("LOCATION PERMISSION RESULT", "Failed")
                } else {
                    Log.i("LOCATION PERMISSION RESULT", "Succeed")
                }
            },
        )


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
            HomeMapView(
                modifier = Modifier.fillMaxSize(),
                intent = intent,
            )

            HomeTopBar(
                isDriverActive = uiState.isDriverActive,
                speed = uiState.speed,
                onChangeStatus = {
                    intent.invoke(HomePageIntent.ToggleDriverStatus)
                },
            )

            HomeActionButtons(
                visible = sheetState.targetValue == BottomSheetStatus.Collapsed,
                intent = intent
            )
        }
    }
}