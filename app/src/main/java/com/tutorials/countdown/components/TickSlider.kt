package com.tutorials.countdown.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tutorials.countdown.CountDownState

@Composable
fun TickSlider(
    state: CountDownState,
    title: String,
    value: Float,
    steps: Int = 0,
    description: String,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (value: Float) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.width(70.dp),
            text = title
        )
        Slider(
            modifier = Modifier
                .padding(end = 10.dp)
                .weight(2f),
            steps = steps,
            value = value,
            valueRange = range,
            onValueChange = {
                state.pause()
                onValueChange(it)
            },
            onValueChangeFinished = {
                state.stop()
            },
            colors = SliderDefaults.colors(
                activeTickColor = Color.Transparent
            )
        )
        Text(text = description)
    }
}