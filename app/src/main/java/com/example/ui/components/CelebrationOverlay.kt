package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.random.Random

data class ConfettiPiece(
    val startX: Float,
    val speed: Float,
    val size: Float,
    val color: Color,
    val isCircle: Boolean,
    val wobbleSpeed: Float
)

data class Balloon(
    val startX: Float,
    val speed: Float,
    val radius: Float,
    val color: Color
)

@Composable
fun CelebrationOverlay(
    active: Boolean,
    modifier: Modifier = Modifier
) {
    if (!active) return

    var animTime by remember { mutableStateOf(0f) }
    val progress by animateFloatAsState(
        targetValue = animTime,
        animationSpec = tween(durationMillis = 4500, easing = LinearEasing),
        label = "celebrationProgress"
    )

    LaunchedEffect(active) {
        animTime = 1f
    }

    val confetti = remember {
        val colors = listOf(
            Color(0xFFFF3366),
            Color(0xFFFFD166),
            Color(0xFF06D6A0),
            Color(0xFF118AB2),
            Color(0xFF9B5DE5),
            Color(0xFFF15BB5)
        )
        List(60) {
            ConfettiPiece(
                startX = Random.nextFloat(),
                speed = 0.8f + Random.nextFloat() * 1.2f,
                size = 12f + Random.nextFloat() * 14f,
                color = colors.random(),
                isCircle = Random.nextBoolean(),
                wobbleSpeed = 2f + Random.nextFloat() * 4f
            )
        }
    }

    val balloons = remember {
        val colors = listOf(
            Color(0xFFFF6B6B),
            Color(0xFF4ECDC4),
            Color(0xFFFFD166),
            Color(0xFF6C5CE7),
            Color(0xFFFF9FF3)
        )
        List(8) {
            Balloon(
                startX = 0.1f + Random.nextFloat() * 0.8f,
                speed = 0.7f + Random.nextFloat() * 0.6f,
                radius = 35f + Random.nextFloat() * 25f,
                color = colors.random()
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            if (width <= 0 || height <= 0) return@Canvas

            // Draw Confetti Falling
            confetti.forEach { c ->
                val y = (progress * c.speed * height * 1.2f) % (height + 50f) - 50f
                val wobble = kotlin.math.sin(progress * c.wobbleSpeed * Math.PI.toFloat() * 2) * 30f
                val x = width * c.startX + wobble

                if (c.isCircle) {
                    drawCircle(
                        color = c.color,
                        radius = c.size / 2f,
                        center = Offset(x, y)
                    )
                } else {
                    drawRect(
                        color = c.color,
                        topLeft = Offset(x, y),
                        size = Size(c.size, c.size * 0.7f)
                    )
                }
            }

            // Draw Balloons Floating Up
            balloons.forEach { b ->
                val y = height + 100f - (progress * b.speed * (height + 300f))
                val x = width * b.startX + kotlin.math.sin(progress * 4f) * 15f
                drawBalloon(Offset(x, y), b.radius, b.color)
            }
        }
    }
}

private fun DrawScope.drawBalloon(center: Offset, radius: Float, color: Color) {
    // Balloon body
    drawOval(
        color = color,
        topLeft = Offset(center.x - radius * 0.85f, center.y - radius * 1.15f),
        size = Size(radius * 1.7f, radius * 2.3f),
        style = Fill
    )
    // Knot
    drawCircle(
        color = color,
        radius = radius * 0.18f,
        center = Offset(center.x, center.y + radius * 1.18f)
    )
    // String
    drawLine(
        color = Color.LightGray.copy(alpha = 0.8f),
        start = Offset(center.x, center.y + radius * 1.18f),
        end = Offset(center.x + 8f, center.y + radius * 1.9f),
        strokeWidth = 2f
    )
}
