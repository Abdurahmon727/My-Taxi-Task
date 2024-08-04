package com.example.mytaxitask.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mytaxitask.R
import com.example.mytaxitask.core.composables.RoundedButton
import com.example.mytaxitask.core.extensions.Height


@Composable
fun HomeActionButtons(showMe: ()->Unit) {
    val localConfig = LocalConfiguration.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = localConfig.screenHeightDp.dp * .45f),
    ) {
        RoundedButton(
            fillColor = Color(0xFFf5f6f8),
            onClick = {
                //todo
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_chevrons), contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Column {
            RoundedButton(
                fillColor = MaterialTheme.colorScheme.background,
                onClick = {
                    //todo
                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_plus), contentDescription = ""
                )
            }
            16.Height()
            RoundedButton(
                fillColor = MaterialTheme.colorScheme.background,
                onClick = {
                    //todo
                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_minus), contentDescription = ""
                )
            }
            16.Height()
            RoundedButton(
                fillColor = MaterialTheme.colorScheme.background,
                onClick = {
                    showMe.invoke()
                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_navigator), contentDescription = ""
                )
            }
        }
    }
}