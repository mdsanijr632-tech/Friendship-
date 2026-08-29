package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.FloatingHeartsAndStarsCanvas
import com.example.ui.components.PulsatingHeart
import com.example.ui.screens.ArafatBirthdayScreen
import com.example.ui.screens.ArafatMessageDialog
import com.example.ui.screens.FunZoneScreen
import com.example.ui.screens.FutureQuotesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MemoriesScreen
import com.example.ui.screens.PromiseDialog
import com.example.ui.screens.SaniMessageDialog
import com.example.ui.screens.SettingsDialog
import com.example.ui.screens.TimelineScreen
import com.example.ui.theme.FriendshipCoral
import com.example.ui.theme.FriendshipGold
import com.example.ui.theme.FriendshipPurple
import com.example.ui.theme.FriendshipRose
import com.example.ui.theme.SaniArafatTheme
import com.example.ui.viewmodel.FriendshipViewModel

sealed class NavTab(val title: String, val icon: ImageVector, val tag: String) {
    object Home : NavTab("Home", Icons.Default.Home, "tab_home")
    object Timeline : NavTab("Story", Icons.Default.MenuBook, "tab_timeline")
    object Memories : NavTab("Memories", Icons.Default.PhotoLibrary, "tab_memories")
    object Birthday : NavTab("Arafat 🎂", Icons.Default.Cake, "tab_birthday")
    object FunZone : NavTab("Moja 😆", Icons.Default.EmojiEmotions, "tab_fun")
    object FuturePlan : NavTab("Future 🚀", Icons.Default.RocketLaunch, "tab_future")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: FriendshipViewModel = viewModel()
            val settings by viewModel.settings.collectAsStateWithLifecycle()

            val systemDark = isSystemInDarkTheme()
            val isDark = when (settings.darkModeOption) {
                "DARK" -> true
                "LIGHT" -> false
                else -> systemDark
            }

            SaniArafatTheme(darkTheme = isDark) {
                MainAppContent(
                    viewModel = viewModel,
                    isDark = isDark
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContent(
    viewModel: FriendshipViewModel,
    isDark: Boolean
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    // Dialog States
    var showSaniDialog by remember { mutableStateOf(false) }
    var showArafatDialog by remember { mutableStateOf(false) }
    var showPromiseDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val tabs = listOf(
        NavTab.Home,
        NavTab.Timeline,
        NavTab.Memories,
        NavTab.Birthday,
        NavTab.FunZone,
        NavTab.FuturePlan
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Ambient Particle Canvas (Hearts, stars & sparks)
        FloatingHeartsAndStarsCanvas(
            enabled = settings.particlesEnabled,
            darkTheme = isDark
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.92f),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sani ",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            PulsatingHeart(size = 20.dp)
                            Text(
                                text = " Arafat",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    color = FriendshipPurple
                                )
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.85f)
                    ),
                    actions = {
                        IconButton(
                            onClick = {
                                val nextMode = if (isDark) "LIGHT" else "DARK"
                                viewModel.setDarkModeOption(nextMode)
                            },
                            modifier = Modifier.testTag("theme_toggle_btn")
                        ) {
                            Icon(
                                imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Toggle Theme",
                                tint = if (isDark) FriendshipGold else MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(
                            onClick = { showSettingsDialog = true },
                            modifier = Modifier.testTag("settings_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("main_bottom_nav")
                ) {
                    tabs.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = FriendshipRose,
                                selectedTextColor = FriendshipRose,
                                indicatorColor = FriendshipRose.copy(alpha = 0.15f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            ),
                            modifier = Modifier.testTag(tab.tag)
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTabIndex) {
                    0 -> HomeScreen(
                        viewModel = viewModel,
                        onOpenSaniMessage = { showSaniDialog = true },
                        onOpenArafatMessage = { showArafatDialog = true },
                        onOpenPromise = { showPromiseDialog = true },
                        onNavigateToBirthday = { selectedTabIndex = 3 },
                        onNavigateToMemories = { selectedTabIndex = 2 }
                    )
                    1 -> TimelineScreen()
                    2 -> MemoriesScreen(viewModel = viewModel)
                    3 -> ArafatBirthdayScreen(viewModel = viewModel)
                    4 -> FunZoneScreen(viewModel = viewModel)
                    5 -> FutureQuotesScreen(viewModel = viewModel)
                }
            }
        }

        // Dialogs
        if (showSaniDialog) {
            SaniMessageDialog(
                messageText = settings.saniMessageToArafat,
                onSaveMessage = { viewModel.updateSaniMessage(it) },
                onDismiss = { showSaniDialog = false }
            )
        }

        if (showArafatDialog) {
            ArafatMessageDialog(
                messageText = settings.arafatMessageToSani,
                onSaveMessage = { viewModel.updateArafatMessage(it) },
                onDismiss = { showArafatDialog = false }
            )
        }

        if (showPromiseDialog) {
            PromiseDialog(
                onDismiss = { showPromiseDialog = false },
                onPinkySwear = { viewModel.triggerCelebration() }
            )
        }

        if (showSettingsDialog) {
            SettingsDialog(
                viewModel = viewModel,
                onDismiss = { showSettingsDialog = false }
            )
        }
    }
}
