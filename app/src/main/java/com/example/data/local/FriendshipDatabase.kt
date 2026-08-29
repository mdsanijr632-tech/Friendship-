package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [MemoryEntity::class, FriendshipSettingsEntity::class, FutureGoalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FriendshipDatabase : RoomDatabase() {
    abstract fun friendshipDao(): FriendshipDao

    companion object {
        @Volatile
        private var INSTANCE: FriendshipDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FriendshipDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FriendshipDatabase::class.java,
                    "sani_arafat_friendship_db"
                ).addCallback(FriendshipDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class FriendshipDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.friendshipDao())
                    }
                }
            }

            private suspend fun populateInitialData(dao: FriendshipDao) {
                // Initial Settings
                dao.saveSettings(FriendshipSettingsEntity())

                // Initial Memories
                dao.insertMemory(
                    MemoryEntity(
                        title = "Amader Prothom Moja",
                        note = "Jeidin amra prothom ekshathe adda dilam ar eto beshi hashlam je pete betha hoye gechilo! Sei din thekei bujhechilam bondhutto ta special.",
                        dateText = "Late 2023",
                        emojiIcon = "😂",
                        category = "Moja & Masti"
                    )
                )
                dao.insertMemory(
                    MemoryEntity(
                        title = "Chai Adda & Serious Talk",
                        note = "Rater bela tong-er dokane bose cha khete khete future-er dream niye onek kotha bolar sei shundor raat.",
                        dateText = "Early 2024",
                        emojiIcon = "☕",
                        category = "Late Night Adda"
                    )
                )
                dao.insertMemory(
                    MemoryEntity(
                        title = "Boro Jhogra & Abar Mile Jawa",
                        note = "Ekta choto bishoy niye 3 din kotha bondho chilo. Tarpor Arafat hashi mukhe ese bollo—'Ei pagol, cha khabi na?'. Sob rag ek nimishe sesh!",
                        dateText = "Mid 2024",
                        emojiIcon = "🤝",
                        category = "Special Moment"
                    )
                )

                // Initial Future Goals
                dao.insertFutureGoals(
                    listOf(
                        FutureGoalEntity(title = "Ekdin ekshathe shundor kono jaygay ghurte jawa 🏖️", iconEmoji = "🏖️"),
                        FutureGoalEntity(title = "Onek notun memory create kora ar photo tula 📸", iconEmoji = "📸"),
                        FutureGoalEntity(title = "Purono din gulo mone kore pet fatiye hasha 😂", iconEmoji = "😂"),
                        FutureGoalEntity(title = "Nijeder sob personal dream ar goal fulfill kora 🌟", iconEmoji = "🌟"),
                        FutureGoalEntity(title = "Bondhutto jeno bochorer por bochor ekoi rokom thake ♾️", iconEmoji = "♾️"),
                        FutureGoalEntity(title = "Future-e abar ei app open kore purono memories dekha 📱", iconEmoji = "📱")
                    )
                )
            }
        }
    }
}
