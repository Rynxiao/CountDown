package com.tutorials.countdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import com.tutorials.countdown.components.ActionRow
import com.tutorials.countdown.components.TickWheel
import com.tutorials.countdown.ui.theme.bgColorCenter

const val StartRadiusFraction = 0.5f
const val EndRadiusFraction = 0.75f
const val TickWidth = 9f

@Composable
fun Countdown() {
    val scope = rememberCoroutineScope()
    val state = remember { CountDownState(scope = scope, counts = 30) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(listOf(bgColorCenter, state.bgEdge))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TickWheel(ticks = 40, state = state)
        ActionRow(state = state)
    }
}