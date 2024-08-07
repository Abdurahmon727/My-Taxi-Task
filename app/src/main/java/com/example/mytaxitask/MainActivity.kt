package com.example.mytaxitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mytaxitask.data.LocalStorage
import com.example.mytaxitask.presentation.home.HomePage
import com.example.mytaxitask.presentation.home.HomePageViewModel
import com.example.mytaxitask.presentation.theme.MyTaxiTaskTheme
import com.example.mytaxitask.service.LocationService
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTaxiTaskTheme {
                val context = LocalContext.current
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val fusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(
                            application
                        )

                    val viewModel = HomePageViewModel(
                        locationService = LocationService(
                            application = application,
                            fusedLocationProviderClient = fusedLocationProviderClient
                        ), localStorage = LocalStorage(context)
                    )
                    HomePage(viewModel = viewModel).Content()
                }
            }
        }
    }
}