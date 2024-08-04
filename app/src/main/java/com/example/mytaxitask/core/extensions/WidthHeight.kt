package com.example.mytaxitask.core.extensions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Int.Width() {
    return Spacer(modifier = Modifier.width(this.dp))
}

@Composable
fun Int.Height() {
    return Spacer(modifier = Modifier.height(this.dp))
}