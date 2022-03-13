package com.tutorials.countdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import com.tutorials.countdown.components.ActionRow
import com.tutorials.countdown.components.TickSlider
import com.tutorials.countdown.components.TickWheel
import com.tutorials.countdown.ui.theme.bgColorCenter

@Composable
fun Countdown() {
    val scope = rememberCoroutineScope()
    val state = remember { CountDownState(scope = scope) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(listOf(bgColorCenter, state.bgEdge))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TickWheel(state = state)
        TickSlider(
            state = state,
            title = "ticks",
            value = state.ticks.toFloat(),
            description = "${state.ticks}",
            range = 30f..60f
        ) {
            state.ticks = it.toInt()
        }
        TickSlider(
            state = state,
            title = "seconds",
            value = state.totalSeconds.toFloat(),
            description = "${state.totalSeconds}s",
            range = 1f..360f
        ) {
            state.totalSeconds = it.toInt()
            state.angleInEverySecond = 360 / it
        }
        ActionRow(state = state)
    }
}