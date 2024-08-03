package com.example.mytaxitask.ui.home

import androidx.lifecycle.ViewModel


class HomeViewModel : ViewModel() {

}

data class HomeUiState(
    val status: MapStatus,
)

enum class MapStatus{initial}