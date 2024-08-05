package com.example.mytaxitask.core.constants

import com.example.mytaxitask.R
import com.example.mytaxitask.domain.model.BottomSheetItemModel
import com.example.mytaxitask.domain.model.DriverStatus
import com.example.mytaxitask.presentation.theme.green
import com.example.mytaxitask.presentation.theme.red

object AppConstants {
    const val MAPURL = "mapbox://styles/abdurahmon727/clzdt32n200c601qybtb6b8yf"

    val driverStatuses = listOf(
        DriverStatus(R.string.busy, red), DriverStatus(R.string.active, green)
    )

    val homeBottomSheetItems = listOf(
        BottomSheetItemModel(
            iconRes = R.drawable.ic_tariff, titleRes = R.string.tarif, trailingText = "6 / 8"
        ), BottomSheetItemModel(
            iconRes = R.drawable.ic_order, titleRes = R.string.orders, trailingText = "0"
        ), BottomSheetItemModel(
            iconRes = R.drawable.ic_rocket, titleRes = R.string.rocket, trailingText = ""
        )
    )
}