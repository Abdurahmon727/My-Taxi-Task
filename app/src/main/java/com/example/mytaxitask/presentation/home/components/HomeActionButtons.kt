package com.example.mytaxitask.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.example.mytaxitask.R
import com.example.mytaxitask.core.composables.RoundedButton
import com.example.mytaxitask.core.extensions.Height
import com.example.mytaxitask.presentation.home.HomePageIntent


@Composable
fun HomeActionButtons(visible: Boolean, intent: (HomePageIntent) -> Unit) {
    val localConfig = LocalConfiguration.current
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = localConfig.screenHeightDp.dp * .45f),
    ) {
        AnimatedVisibility(
            visible = visible,
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
            ) + fadeOut(),
            enter = slideInHorizontally(
                initialOffsetX = { -it },
            ) + fadeIn()
        ) {
            RoundedButton(
                fillColor = MaterialTheme.colorScheme.secondaryContainer,
                onClick = {
                    //todo
                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_chevrons),
                    contentDescription = ""
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(
            visible = visible,
            exit = slideOutHorizontally(
                targetOffsetX = { it },
            ) + fadeOut(),
            enter = slideInHorizontally(
                initialOffsetX = { it },
            ) + fadeIn()
        ) {
            Column {
                RoundedButton(
                    fillColor = MaterialTheme.colorScheme.background,
                    onClick = {
                        intent.invoke(HomePageIntent.MapZoomIn)
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
                        intent.invoke(HomePageIntent.MapZoomOut)
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
                        val marker = context.getDrawable(R.drawable.ic_car)!!.toBitmap()
                        intent.invoke(HomePageIntent.ShowMyLocation(marker))
                    },
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_navigator),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}