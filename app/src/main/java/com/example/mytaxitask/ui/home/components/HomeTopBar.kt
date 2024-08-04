package com.example.mytaxitask.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mytaxitask.R
import com.example.mytaxitask.core.composables.RoundedButton
import com.example.mytaxitask.core.extensions.Width
import com.example.mytaxitask.core.extensions.advancedShadow
import com.example.mytaxitask.ui.home.DriverStatus
import com.example.mytaxitask.ui.theme.green
import com.example.mytaxitask.ui.theme.shadowColor


@Composable
fun HomeTopBar(selectedTabIndex: MutableIntState, driverStatuses: List<DriverStatus>) {

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        DriverStatusIndicator(
            Modifier.myTabIndicatorOffset(tabPositions[selectedTabIndex.intValue]),
            tabIndex = selectedTabIndex.intValue,
            tabs = driverStatuses,
        )
    }

    Row(modifier = Modifier.padding(16.dp)) {
        RoundedButton(
            fillColor = MaterialTheme.colorScheme.background,
            onClick = {
                //todo
            },
        ) {
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
                selectedTabIndex = selectedTabIndex.intValue,
                indicator = indicator,
                divider = {},
            ) {
                driverStatuses.onEachIndexed { index, tab ->
                    DriverStatusTab(
                        index = index,
                        tab = tab,
                        selectedTabIndex = selectedTabIndex
                    )
                }
            }
        }
        12.Width()
        RoundedButton(
            fillColor = green
        ) {
            Text(
                text = "95", style = MaterialTheme.typography.headlineMedium,
            )
        }
    }
}