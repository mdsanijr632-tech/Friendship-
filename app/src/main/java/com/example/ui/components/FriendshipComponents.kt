package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FriendshipCoral
import com.example.ui.theme.FriendshipPurple
import com.example.ui.theme.FriendshipRose
import com.example.ui.theme.HeartRed

@Composable
fun GlowingFriendshipCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    glowColor: Color = FriendshipRose,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    elevation: Dp = 6.dp,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = glowColor.copy(alpha = 0.35f),
                spotColor = glowColor.copy(alpha = 0.45f)
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(
                        glowColor.copy(alpha = 0.6f),
                        FriendshipPurple.copy(alpha = 0.3f),
                        glowColor.copy(alpha = 0.1f)
                    )
                ),
                shape = shape
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        content()
    }
}

@Composable
fun PulsatingHeart(
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    tint: Color = HeartRed
) {
    val infiniteTransition = rememberInfiniteTransition(label = "heartPulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartScale"
    )

    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "Glowing Heart",
        tint = tint,
        modifier = modifier
            .size(size)
            .scale(scale)
    )
}

@Composable
fun TimeCounterUnit(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    tintColor: Color = FriendshipRose
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f))
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                RoundedCornerShape(16.dp)
            )
            .padding(vertical = 10.dp, horizontal = 8.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = tintColor
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FriendshipLiveCounter(
    years: Int,
    months: Int,
    days: Int,
    hours: Int,
    minutes: Int,
    seconds: Int,
    modifier: Modifier = Modifier
) {
    GlowingFriendshipCard(
        modifier = modifier
            .fillMaxWidth()
            .testTag("friendship_live_counter"),
        glowColor = FriendshipRose
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "⏳ Amader Bondhutter Somoy",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "2023–2024 theke aj porjonto ekshathe ❤️",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Row 1: Years, Months, Days
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimeCounterUnit(
                    value = String.format("%02d", years),
                    label = "Bochor (Y)",
                    modifier = Modifier.weight(1f),
                    tintColor = FriendshipRose
                )
                TimeCounterUnit(
                    value = String.format("%02d", months),
                    label = "Mash (M)",
                    modifier = Modifier.weight(1f),
                    tintColor = FriendshipCoral
                )
                TimeCounterUnit(
                    value = String.format("%02d", days),
                    label = "Din (D)",
                    modifier = Modifier.weight(1f),
                    tintColor = FriendshipPurple
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 2: Hours, Minutes, Seconds
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimeCounterUnit(
                    value = String.format("%02d", hours),
                    label = "Ghonta (H)",
                    modifier = Modifier.weight(1f),
                    tintColor = MaterialTheme.colorScheme.tertiary
                )
                TimeCounterUnit(
                    value = String.format("%02d", minutes),
                    label = "Minute (M)",
                    modifier = Modifier.weight(1f),
                    tintColor = FriendshipCoral
                )
                TimeCounterUnit(
                    value = String.format("%02d", seconds),
                    label = "Second (S)",
                    modifier = Modifier.weight(1f),
                    tintColor = FriendshipRose
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Live status badge
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00B894))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Live Counting • Protiti second amader bondhutto aro deep hocche",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun BanglishBadge(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = containerColor,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = contentColor
            ),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
