package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.random.Random

data class Particle(
    val initialXRatio: Float,
    val speedRatio: Float,
    val size: Float,
    val alpha: Float,
    val isHeart: Boolean,
    val colorIndex: Int
)

@Composable
fun FloatingHeartsAndStarsCanvas(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    darkTheme: Boolean = false
) {
    if (!enabled) return

    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particleProgress"
    )

    val particles = remember {
        List(25) {
            Particle(
                initialXRatio = Random.nextFloat(),
                speedRatio = 0.5f + Random.nextFloat() * 0.9f,
                size = 10f + Random.nextFloat() * 16f,
                alpha = 0.25f + Random.nextFloat() * 0.45f,
                isHeart = Random.nextBoolean(),
                colorIndex = Random.nextInt(4)
            )
        }
    }

    val particleColors = remember(darkTheme) {
        if (darkTheme) {
            listOf(
                Color(0xFFFF5E82),
                Color(0xFFA29BFE),
                Color(0xFFFFD166),
                Color(0xFF74B9FF)
            )
        } else {
            listOf(
                Color(0xFFFF4B72),
                Color(0xFF6C5CE7),
                Color(0xFFF39C12),
                Color(0xFF0984E3)
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        if (width <= 0 || height <= 0) return@Canvas

        particles.forEach { p ->
            val yProgress = (progress * p.speedRatio + p.initialXRatio) % 1.0f
            val currentY = height * (1.0f - yProgress)
            val currentX = width * p.initialXRatio + kotlin.math.sin(yProgress * Math.PI.toFloat() * 4) * 20f
            val color = particleColors[p.colorIndex % particleColors.size].copy(alpha = p.alpha)

            if (p.isHeart) {
                drawMiniHeart(
                    center = Offset(currentX, currentY),
                    size = p.size,
                    color = color
                )
            } else {
                drawMiniStar(
                    center = Offset(currentX, currentY),
                    radius = p.size * 0.7f,
                    color = color
                )
            }
        }
    }
}

private fun DrawScope.drawMiniHeart(center: Offset, size: Float, color: Color) {
    val path = Path()
    val x = center.x
    val y = center.y
    val s = size / 2f

    path.moveTo(x, y + s)
    path.cubicTo(x - s * 1.2f, y, x - s * 1.5f, y - s, x, y - s * 0.5f)
    path.cubicTo(x + s * 1.5f, y - s, x + s * 1.2f, y, x, y + s)
    path.close()

    drawPath(path = path, color = color, style = Fill)
}

private fun DrawScope.drawMiniStar(center: Offset, radius: Float, color: Color) {
    val path = Path()
    val points = 4
    val innerRadius = radius * 0.4f

    for (i in 0 until points * 2) {
        val r = if (i % 2 == 0) radius else innerRadius
        val angle = (i * Math.PI / points).toFloat() - (Math.PI.toFloat() / 2f)
        val px = center.x + r * kotlin.math.cos(angle)
        val py = center.y + r * kotlin.math.sin(angle)
        if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
    }
    path.close()
    drawPath(path = path, color = color, style = Fill)
}
