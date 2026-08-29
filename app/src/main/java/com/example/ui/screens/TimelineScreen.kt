package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FriendshipDataDefaults
import com.example.data.model.TimelineStoryItem
import com.example.ui.components.BanglishBadge
import com.example.ui.components.GlowingFriendshipCard
import com.example.ui.theme.FriendshipCoral
import com.example.ui.theme.FriendshipGold
import com.example.ui.theme.FriendshipPurple
import com.example.ui.theme.FriendshipRose

@Composable
fun TimelineScreen(
    modifier: Modifier = Modifier
) {
    val stories = FriendshipDataDefaults.timelineStories
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("timeline_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Screen Header
        GlowingFriendshipCard(
            modifier = Modifier.fillMaxWidth(),
            glowColor = FriendshipPurple
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "📖", fontSize = 36.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Amader Friendship Story",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Prothom porichoy theke aj porjonto sob sriti gulo...",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Timeline Items
        stories.forEachIndexed { index, item ->
            val isLast = index == stories.size - 1
            val stepColor = when (index % 4) {
                0 -> FriendshipRose
                1 -> FriendshipPurple
                2 -> FriendshipCoral
                else -> FriendshipGold
            }

            TimelineStoryRow(
                item = item,
                stepNumber = index + 1,
                accentColor = stepColor,
                isLast = isLast
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Inspiring Banner
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "✨ Aro onek notun chapter baki...",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Amader ei bondhutto jeno shara jibon erokom anonde ar bhalobashay chole. InshaAllah amader golpo kokhono sesh hobe na!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun TimelineStoryRow(
    item: TimelineStoryItem,
    stepNumber: Int,
    accentColor: Color,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Left Column: Node & Connecting Line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(44.dp)
        ) {
            // Node Icon
            Surface(
                shape = CircleShape,
                color = accentColor,
                modifier = Modifier
                    .size(38.dp)
                    .border(3.dp, MaterialTheme.colorScheme.background, CircleShape)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = item.emoji, fontSize = 18.sp)
                }
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .fillMaxHeight()
                        .padding(vertical = 4.dp)
                        .background(accentColor.copy(alpha = 0.4f))
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Right Column: Card Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 20.dp)
        ) {
            GlowingFriendshipCard(
                modifier = Modifier.fillMaxWidth(),
                glowColor = accentColor,
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        BanglishBadge(
                            text = item.tag,
                            containerColor = accentColor.copy(alpha = 0.18f),
                            contentColor = accentColor
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = accentColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f)
                        )
                    )
                }
            }
        }
    }
}
