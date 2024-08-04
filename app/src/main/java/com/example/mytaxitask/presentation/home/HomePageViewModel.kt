package com.example.mytaxitask.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class HomePageViewModel : ViewModel() {
    val state = MutableStateFlow(HomePageState())


    fun onIntentDispatched(intent: HomePageIntent) {
        when (intent) {
            is HomePageIntent.Init -> {

            }

            is HomePageIntent.ShowMyLocation -> showMyLocation()
            is HomePageIntent.ToggleDriverStatus -> {
                state.update { it.copy(isDriverActive = !state.value.isDriverActive) }
            }
        }
    }

    private fun init() {

    }

    private fun showMyLocation() {
        state.value.mapView.let {

        }
    }

}

