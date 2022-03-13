package com.tutorials.countdown.components

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.tutorials.countdown.CountDownState
import com.tutorials.countdown.ui.theme.darkOrange
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

const val StartRadiusFraction = 0.5f
const val EndRadiusFraction = 0.75f
const val TickWidth = 9f
const val AnimationSecond = 10

@Composable
fun TickWheel(state: CountDownState) {
    val transition = rememberInfiniteTransition()
    val animatedWheel by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )

    Box(
        Modifier
            .fillMaxWidth()
            .rotate(-90f)
            .background(Color.Transparent, shape = CircleShape)
            .aspectRatio(1f)
            .scale(if (state.secondsLeft < AnimationSecond) animatedWheel else 0.9f)
            .drawBehind {
                val startRadius = size.width / 2 * StartRadiusFraction
                val endRadius = size.width / 2 * EndRadiusFraction

                for (i in 0 until state.ticks) {
                    val angle = i * (360f / state.ticks)
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
            modifier = Modifier.rotate(90f),
            text = state.text,
            style = TextStyle(
                color = Color.White,
                fontSize = 48.sp
            )
        )
    }
}