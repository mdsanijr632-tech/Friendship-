package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.BanglishBadge
import com.example.ui.components.GlowingFriendshipCard
import com.example.ui.theme.FriendshipCoral
import com.example.ui.theme.FriendshipGold
import com.example.ui.theme.FriendshipPurple
import com.example.ui.theme.FriendshipRose
import com.example.ui.theme.FriendshipSkyBlue
import com.example.ui.viewmodel.FriendshipViewModel

@Composable
fun FunZoneScreen(
    viewModel: FriendshipViewModel,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val funnyMessage by viewModel.currentFunnyMessage.collectAsStateWithLifecycle()
    val randomQuestion by viewModel.currentRandomQuestion.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("fun_zone_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header
        GlowingFriendshipCard(
            modifier = Modifier.fillMaxWidth(),
            glowColor = FriendshipGold
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "🤡", fontSize = 36.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Moja & Friendship Fun Zone",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Vote dao, quiz khelo ar ekshathe pet fatiye hasho!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section: "Ke Beshi...?" Polls
        Text(
            text = "📊 Friendship Polls (Vote Koro!)",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 1. "Ke beshi pagol? 😆"
        FriendshipPollCard(
            question = "Ke beshi pagol? 😆",
            pollId = "pagol",
            saniVotes = settings.votePagolSani,
            arafatVotes = settings.votePagolArafat,
            emoji = "🤪",
            onVote = { isSani -> viewModel.votePoll("pagol", isSani) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 2. "Ke beshi rag kore?"
        FriendshipPollCard(
            question = "Ke beshi rag kore? 😡",
            pollId = "rag",
            saniVotes = settings.voteRagSani,
            arafatVotes = settings.voteRagArafat,
            emoji = "😤",
            onVote = { isSani -> viewModel.votePoll("rag", isSani) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 3. "Ke age sorry bole?"
        FriendshipPollCard(
            question = "Ke age sorry bole? 🥺",
            pollId = "sorry",
            saniVotes = settings.voteSorrySani,
            arafatVotes = settings.voteSorryArafat,
            emoji = "🕊️",
            onVote = { isSani -> viewModel.votePoll("sorry", isSani) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 4. "Ke beshi moja kore?"
        FriendshipPollCard(
            question = "Ke beshi moja kore? 🤡",
            pollId = "moja",
            saniVotes = settings.voteMojaSani,
            arafatVotes = settings.voteMojaArafat,
            emoji = "🎉",
            onVote = { isSani -> viewModel.votePoll("moja", isSani) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Section: Random Funny Friendship Messages
        GlowingFriendshipCard(
            modifier = Modifier.fillMaxWidth(),
            glowColor = FriendshipCoral
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🤣", fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Random Funny Message",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    IconButton(
                        onClick = { viewModel.nextFunnyMessage() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Change Message",
                            tint = FriendshipCoral
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = funnyMessage,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            lineHeight = 24.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { viewModel.nextFunnyMessage() },
                    colors = ButtonDefaults.buttonColors(containerColor = FriendshipCoral),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("next_funny_msg_btn")
                ) {
                    Icon(imageVector = Icons.Default.EmojiEmotions, contentDescription = "Next")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Aro Ekta Funny Kotha Dekho 🤣",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section: Random Friendship Questions
        GlowingFriendshipCard(
            modifier = Modifier.fillMaxWidth(),
            glowColor = FriendshipPurple
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🎲", fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Friendship Question Generator",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = FriendshipPurple
                            )
                        )
                    }

                    IconButton(
                        onClick = { viewModel.nextRandomQuestion() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Next Question",
                            tint = FriendshipPurple
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "❓ Tor Jonno Proshno:",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = FriendshipPurple
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "“$randomQuestion”",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 24.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { viewModel.nextRandomQuestion() },
                    colors = ButtonDefaults.buttonColors(containerColor = FriendshipPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("next_question_btn")
                ) {
                    Icon(imageVector = Icons.Default.Casino, contentDescription = "Dice")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Notun Question Dekho 🎲",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun FriendshipPollCard(
    question: String,
    pollId: String,
    saniVotes: Int,
    arafatVotes: Int,
    emoji: String,
    onVote: (isSani: Boolean) -> Unit
) {
    val total = (saniVotes + arafatVotes).coerceAtLeast(1)
    val saniPercent = ((saniVotes.toFloat() / total.toFloat()) * 100).toInt()
    val arafatPercent = 100 - saniPercent

    GlowingFriendshipCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("poll_$pollId"),
        glowColor = FriendshipCoral,
        shape = RoundedCornerShape(18.dp)
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
                    text = question,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(text = emoji, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Percentage Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sani: $saniPercent% ($saniVotes votes)",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = FriendshipRose
                    )
                )
                Text(
                    text = "Arafat: $arafatPercent% ($arafatVotes votes)",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = FriendshipPurple
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { saniVotes.toFloat() / total.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = FriendshipRose,
                trackColor = FriendshipPurple,
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Voting Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onVote(true) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "👉 Sani",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                OutlinedButton(
                    onClick = { onVote(false) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "👉 Arafat",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
