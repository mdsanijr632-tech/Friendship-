package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.ui.components.BanglishBadge
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.GlowingFriendshipCard
import com.example.ui.components.TimeCounterUnit
import com.example.ui.theme.CakeGold
import com.example.ui.theme.FriendshipCoral
import com.example.ui.theme.FriendshipGold
import com.example.ui.theme.FriendshipPurple
import com.example.ui.theme.FriendshipRose
import com.example.ui.viewmodel.FriendshipViewModel

@Composable
fun ArafatBirthdayScreen(
    viewModel: FriendshipViewModel,
    modifier: Modifier = Modifier
) {
    val countdown by viewModel.birthdayCountdown.collectAsStateWithLifecycle()
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val celebrationActive by viewModel.celebrationActive.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    var wishText by remember(settings.arafatWishText) {
        mutableStateOf(settings.arafatWishText)
    }
    var wishSavedSuccess by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .testTag("arafat_birthday_screen"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Hero Birthday Celebration Card
            GlowingFriendshipCard(
                modifier = Modifier.fillMaxWidth(),
                glowColor = CakeGold
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Birthday Header Badge
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = CakeGold.copy(alpha = 0.2f),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(text = "🎈", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Special Birthday Corner",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }

                    // Main Header as requested: "Happy Birthday Arafat 🎂"
                    Text(
                        text = "Happy Birthday Arafat 🎂",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Date as requested: "18 August"
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "📅 ", fontSize = 18.sp)
                        Text(
                            text = "18 August",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = FriendshipCoral
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cake Illustration with Glow
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .border(
                                3.dp,
                                Brush.sweepGradient(
                                    listOf(CakeGold, FriendshipRose, FriendshipPurple, CakeGold)
                                ),
                                CircleShape
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arafat_birthday_cake),
                            contentDescription = "Arafat Birthday Cake",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Celebrate Action Button with Confetti & Balloon animation
                    Button(
                        onClick = { viewModel.triggerCelebration() },
                        colors = ButtonDefaults.buttonColors(containerColor = CakeGold),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("celebrate_birthday_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Celebration,
                            contentDescription = "Celebrate",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Celebrate Arafat-er Birthday! 🎉",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Live Birthday Countdown Section
            GlowingFriendshipCard(
                modifier = Modifier.fillMaxWidth(),
                glowColor = FriendshipRose
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (countdown.isBirthdayToday) "🎉 AJKE ARAFAT-ER BIRTHDAY! 🎂" else "⏳ Next Birthday Countdown",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (countdown.isBirthdayToday) "Sobai mile Arafat k wish koro!" else "18 August ashte ar baki ache:",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TimeCounterUnit(
                            value = "${countdown.days}",
                            label = "Din (Days)",
                            modifier = Modifier.weight(1f),
                            tintColor = FriendshipRose
                        )
                        TimeCounterUnit(
                            value = "${countdown.hours}",
                            label = "Ghonta (Hrs)",
                            modifier = Modifier.weight(1f),
                            tintColor = FriendshipCoral
                        )
                        TimeCounterUnit(
                            value = "${countdown.minutes}",
                            label = "Minute",
                            modifier = Modifier.weight(1f),
                            tintColor = FriendshipPurple
                        )
                        TimeCounterUnit(
                            value = "${countdown.seconds}",
                            label = "Second",
                            modifier = Modifier.weight(1f),
                            tintColor = CakeGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Arafat's Personality Card
            GlowingFriendshipCard(
                modifier = Modifier.fillMaxWidth(),
                glowColor = FriendshipPurple
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = FriendshipPurple.copy(alpha = 0.15f),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "👑", fontSize = 20.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Arafat-er Personality 🌟",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = "Amader sobcheye beloved bondhu",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Personality bullets
                    val traits = listOf(
                        "🌿 Simple: Kono show-off nai, ekdom mon khule kotha bole.",
                        "🤗 Friendly: Jekono manusher sathe shohoje mishe jete pare.",
                        "🤣 A Little Funny: Majhe majhe emon moja korbe je hashte hashte pet betha hobe!",
                        "🤝 Loyal: Bondhutto niye kokhono kono compromise kore na."
                    )

                    traits.forEach { trait ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = trait,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 22.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Birthday Wish Box from Sani to Arafat
            GlowingFriendshipCard(
                modifier = Modifier.fillMaxWidth(),
                glowColor = FriendshipCoral
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "✍️ Arafat-er Jonno Birthday Wish / Note",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Arafat k tar birthday-te shundor ekta message likhe save kore rakho:",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = wishText,
                        onValueChange = {
                            wishText = it
                            wishSavedSuccess = false
                        },
                        label = { Text("Birthday Wish / Valobashar Message") },
                        placeholder = { Text("Subho Jonmodin Arafat! Shob somoy hashi-khushi thak...") },
                        minLines = 3,
                        maxLines = 6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("birthday_wish_input"),
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (wishSavedSuccess) {
                            Text(
                                text = "✓ Wish Saved Successfully! ❤️",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00B894)
                                )
                            )
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        Button(
                            onClick = {
                                viewModel.updateBirthdayWish(wishText)
                                wishSavedSuccess = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = FriendshipRose),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("save_wish_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Wish Save Koro",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Fullscreen celebratory Confetti & Balloon layer when triggered
        CelebrationOverlay(active = celebrationActive)
    }
}
