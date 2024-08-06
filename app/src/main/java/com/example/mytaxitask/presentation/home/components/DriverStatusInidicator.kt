package com.example.mytaxitask.presentation.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.mytaxitask.domain.model.DriverStatus


@Composable
fun DriverStatusIndicator(modifier: Modifier = Modifier, tabIndex: Int, tabs: List<DriverStatus>) {

    val animatedColor =
        animateColorAsState(
            targetValue = tabs[tabIndex].color,
            label = "",
            animationSpec =
            tween(500, easing = FastOutSlowInEasing)
        )
    Box(
        modifier
            .zIndex(-1f)
            .fillMaxWidth().height(46.dp)
            .background(
                animatedColor.value,
                RoundedCornerShape(10.dp),
            )
    )
}

fun Modifier.myTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing), label = ""
    )
    wrapContentSize(Alignment.CenterStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}
