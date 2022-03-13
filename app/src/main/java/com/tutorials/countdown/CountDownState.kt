package com.tutorials.countdown

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tutorials.countdown.ui.theme.bgColorEdge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                val alphaFraction = ((counts - nextSecond) / counts.toFloat()) * 0.65f

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