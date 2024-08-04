package com.example.mytaxitask.ui.home.components

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.mytaxitask.data.models.DriverStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@Composable
fun DriverStatusTab(tab: DriverStatus, isSelected: Boolean, onChange: () -> Unit) {
    Tab(
        text = {
            Text(
                text = stringResource(id = tab.statusTitle),
                style = if (isSelected) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        },
        selected = isSelected,
        onClick = {
            if (!isSelected)
                onChange.invoke()
        },
        interactionSource = object : MutableInteractionSource {
            override val interactions: Flow<Interaction> = emptyFlow()

            override suspend fun emit(interaction: Interaction) {}

            override fun tryEmit(interaction: Interaction) = true
        }
    )
}