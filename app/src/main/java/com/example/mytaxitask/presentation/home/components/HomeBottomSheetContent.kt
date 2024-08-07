package com.example.mytaxitask.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mytaxitask.core.constants.AppConstants
import com.example.mytaxitask.core.extensions.Width
import com.example.mytaxitask.domain.model.BottomSheetItemModel


@Composable
fun HomeBottomSheetContent() {
    val items = AppConstants.homeBottomSheetItems

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(items) { index, item ->
            ItemUI(
                item = item,
                hasTopLine = index != 0,
                trailingColor = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
private fun ItemUI(item: BottomSheetItemModel, hasTopLine: Boolean, trailingColor: Color) {
    Column {
        if (hasTopLine) HorizontalDivider(color = trailingColor.copy())
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = item.iconRes), contentDescription = "",
                colorFilter = ColorFilter.tint(trailingColor)
            )
            8.Width()
            Text(
                text = stringResource(id = item.titleRes),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = item.trailingText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W600,
                    color = trailingColor,
                )
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "",
                tint = trailingColor,
            )
        }
    }
}