package com.example.mytaxitask.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mytaxitask.core.extensions.advancedShadow
import com.example.mytaxitask.core.extensions.ifElse
import com.example.mytaxitask.presentation.theme.shadowColor


@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.background,
    fillColor: Color = Color.White,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .advancedShadow(
                color = shadowColor,
                offsetX = 0.dp,
                offsetY = 8.dp,
                blurRadius = 11.dp,
            )
            .clip(RoundedCornerShape(14.dp))
            .background(borderColor)
            .ifElse(onClick != null, Modifier.clickable { onClick?.invoke() })
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(fillColor),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
