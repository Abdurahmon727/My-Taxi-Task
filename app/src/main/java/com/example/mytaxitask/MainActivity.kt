package com.example.mytaxitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mytaxitask.presentation.home.HomePage
import com.example.mytaxitask.presentation.home.HomePageViewModel
import com.example.mytaxitask.presentation.theme.MyTaxiTaskTheme
import com.example.mytaxitask.service.LocationService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTaxiTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = HomePageViewModel(locationService = LocationService())
                    HomePage(viewModel = viewModel).Content()
                }
            }
        }
    }
}