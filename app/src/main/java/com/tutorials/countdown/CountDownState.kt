package com.tutorials.countdown

import android.text.format.DateUtils
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tutorials.countdown.ui.theme.bgColorEdge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountDownState(private val scope: CoroutineScope) {
    var totalSeconds by mutableStateOf(60)
    var ticks by mutableStateOf(60)
    var angleInEverySecond by mutableStateOf(360f / totalSeconds)

    private var job: Job? = null
    private var currentSeconds by mutableStateOf(totalSeconds)
    var tickTheta by mutableStateOf(0f)
    var text: String by mutableStateOf(DateUtils.formatElapsedTime(totalSeconds.toLong()))
    var isPaused by mutableStateOf(true)
    var bgEdge by mutableStateOf(bgColorEdge.copy(currentSeconds / totalSeconds.toFloat()))

    fun start() {
        isPaused = false
        job = scope.launch {
            while (currentSeconds > 0 && !isPaused) {
                delay(1000)
                val nextSecond = currentSeconds - 1
                val alphaFraction = ((totalSeconds - nextSecond) / totalSeconds.toFloat()) * 0.65f

                currentSeconds = nextSecond
                tickTheta = (360 - angleInEverySecond * nextSecond).toFloat()
                text = DateUtils.formatElapsedTime(nextSecond.toLong())
                bgEdge = bgColorEdge.copy(nextSecond / totalSeconds.toFloat() + alphaFraction)
            }

            if (currentSeconds == 0) {
                stop()
            }
        }
    }

    fun pause() {
        if (!isPaused || job != null) {
            isPaused = true
            job?.cancel()
            job = null
        }
    }

    fun stop() {
        pause()
        currentSeconds = totalSeconds
        tickTheta = 0f
        text = DateUtils.formatElapsedTime(totalSeconds.toLong())
        bgEdge = bgColorEdge
    }
}