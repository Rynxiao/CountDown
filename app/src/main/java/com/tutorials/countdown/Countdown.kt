package com.tutorials.countdown

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import com.tutorials.countdown.ui.theme.bgColorCenter
import com.tutorials.countdown.ui.theme.bgColorEdge
import com.tutorials.countdown.ui.theme.darkOrange
import kotlinx.coroutines.*
import kotlin.math.*

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                if (state.isPaused) {
                    state.start()
                } else {
                    state.pause()
                }
            }) {
                Icon(
                    if (state.isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                    if (state.isPaused) "Pause" else "PlayArrow"
                )
            }
            IconButton(onClick = { state.stop() }) {
                Icon(Icons.Default.Stop, "Stop")
            }
        }
    }
}

class CountDownState(
    private val scope: CoroutineScope,
    private val counts: Int
) {
    private var job: Job? = null
    private var seconds by mutableStateOf(counts)
    var tickTheta by mutableStateOf(0f)
    var text by mutableStateOf("${seconds}s")
    var isPaused by mutableStateOf(true)
    var bgEdge by mutableStateOf(bgColorEdge.copy(seconds / counts.toFloat()))

    private val angleInEverySecond = 360 / counts

    fun start() {
        isPaused = false
        job = scope.launch {
            while (seconds > 0 && !isPaused) {
                delay(1000)
                val nextSecond = seconds - 1
                val alphaFraction = ((counts - nextSecond) / counts.toFloat()) / 2f

                seconds = nextSecond
                tickTheta = (360 - angleInEverySecond * nextSecond).toFloat()
                text = "${nextSecond}s"
                bgEdge = bgColorEdge.copy(nextSecond / counts.toFloat() + alphaFraction)
            }

            if (seconds == 0) {
                stop()
            }
        }
    }

    fun pause() {
        isPaused = true
        job?.cancel()
        job = null
    }

    fun stop() {
        pause()
        seconds = counts
        tickTheta = 0f
        text = "${counts}s"
        bgEdge = bgColorEdge
    }
}

@Composable
fun TickWheel(ticks: Int, state: CountDownState) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.Transparent, shape = CircleShape)
            .aspectRatio(1f)
            .drawBehind {
                val startRadius = size.width / 2 * StartRadiusFraction
                val endRadius = size.width / 2 * EndRadiusFraction

                for (i in 0 until ticks) {
                    val angle = i * (360 / ticks)
                    val on = angle < state.tickTheta
                    val theta = angle * PI.toFloat() / 180f
                    val startPos = Offset(
                        cos(theta) * startRadius,
                        sin(theta) * startRadius
                    )
                    val endPos = Offset(
                        cos(theta) * endRadius,
                        sin(theta) * endRadius,
                    )
                    drawLine(
                        if (on) darkOrange else Color.White.copy(alpha = 0.3f),
                        center + startPos,
                        center + endPos,
                        TickWidth,
                        StrokeCap.Round
                    )
                }

            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = state.text,
            style = TextStyle(
                color = Color.White,
                fontSize = 48.sp
            )
        )
    }
}