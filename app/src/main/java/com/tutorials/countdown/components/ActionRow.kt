package com.tutorials.countdown.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.tutorials.countdown.CountDownState

@Composable
fun ActionRow(state: CountDownState) {
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