package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memories")
data class MemoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val note: String,
    val dateText: String,
    val emojiIcon: String = "📸",
    val photoUri: String? = null,
    val category: String = "Special Moment",
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "friendship_settings")
data class FriendshipSettingsEntity(
    @PrimaryKey
    val id: Int = 1,
    val arafatMessageToSani: String = "Ore Sani bondhu, tui amr best friend! Majhe majhe tor sathe boro jhogra kori, kintu mon theke toke onek respect ar bhalobashi. Ekdin amra duijon mile onek boro kichu korbo inshaAllah. Bondhutto jeno kokhono nosto na hoy! ❤️🤝",
    val saniMessageToArafat: String = "Arafat, tui sudhu amr bondhu na, tui amr vaiyer moto. Tor simple mon ar funny kotha gulo amader bondhutto k sob somoy special banay. Jhogra holeo mone rakhbi, Sani kokhono tor pashe theke shorbe na. Forever friends! 💌✨",
    val friendshipStartDateMillis: Long = 1698840000000L, // Nov 1, 2023
    val darkModeOption: String = "SYSTEM", // "SYSTEM", "LIGHT", "DARK"
    val particlesEnabled: Boolean = true,
    val votePagolSani: Int = 12,
    val votePagolArafat: Int = 18,
    val voteRagSani: Int = 16,
    val voteRagArafat: Int = 7,
    val voteSorrySani: Int = 14,
    val voteSorryArafat: Int = 9,
    val voteMojaSani: Int = 10,
    val voteMojaArafat: Int = 21,
    val arafatWishText: String = ""
)

@Entity(tableName = "future_goals")
data class FutureGoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val isCompleted: Boolean = false,
    val iconEmoji: String = "🚀"
)
