package com.example.mytaxitask.core.constants

import com.example.mytaxitask.R
import com.example.mytaxitask.data.models.DriverStatus
import com.example.mytaxitask.presentation.theme.green
import com.example.mytaxitask.presentation.theme.red

object AppConstants {
    const val MAPURL = "mapbox://styles/abdurahmon727/clzdt32n200c601qybtb6b8yf"

    val driverStatuses = listOf(
        DriverStatus(R.string.busy, red), DriverStatus(R.string.active, green)
    )
}