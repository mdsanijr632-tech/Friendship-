package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.FriendshipDatabase
import com.example.data.local.FriendshipSettingsEntity
import com.example.data.local.FutureGoalEntity
import com.example.data.local.MemoryEntity
import com.example.data.model.FriendshipDataDefaults
import com.example.data.repository.FriendshipRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone

data class FriendshipDuration(
    val years: Int = 0,
    val months: Int = 0,
    val days: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
    val totalDays: Long = 0
)

data class BirthdayCountdown(
    val days: Long = 0,
    val hours: Long = 0,
    val minutes: Long = 0,
    val seconds: Long = 0,
    val isBirthdayToday: Boolean = false
)

class FriendshipViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FriendshipRepository

    val memories: StateFlow<List<MemoryEntity>>
    val settings: StateFlow<FriendshipSettingsEntity>
    val futureGoals: StateFlow<List<FutureGoalEntity>>

    private val _friendshipDuration = MutableStateFlow(FriendshipDuration())
    val friendshipDuration: StateFlow<FriendshipDuration> = _friendshipDuration.asStateFlow()

    private val _birthdayCountdown = MutableStateFlow(BirthdayCountdown())
    val birthdayCountdown: StateFlow<BirthdayCountdown> = _birthdayCountdown.asStateFlow()

    private val _currentRandomQuote = MutableStateFlow(FriendshipDataDefaults.banglishQuotes.first())
    val currentRandomQuote: StateFlow<com.example.data.model.FriendshipQuoteItem> = _currentRandomQuote.asStateFlow()

    private val _currentFunnyMessage = MutableStateFlow(FriendshipDataDefaults.funnyMessages.first())
    val currentFunnyMessage: StateFlow<String> = _currentFunnyMessage.asStateFlow()

    private val _currentRandomQuestion = MutableStateFlow(FriendshipDataDefaults.randomQuestions.first())
    val currentRandomQuestion: StateFlow<String> = _currentRandomQuestion.asStateFlow()

    private val _celebrationActive = MutableStateFlow(false)
    val celebrationActive: StateFlow<Boolean> = _celebrationActive.asStateFlow()

    init {
        val db = FriendshipDatabase.getDatabase(application, viewModelScope)
        repository = FriendshipRepository(db.friendshipDao())

        memories = repository.allMemories.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        settings = repository.settings
            .map { it ?: FriendshipSettingsEntity() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FriendshipSettingsEntity()
            )

        futureGoals = repository.futureGoals.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        startLiveClock()
    }

    private fun startLiveClock() {
        viewModelScope.launch {
            while (isActive) {
                val currentSettings = settings.value
                val startDateMillis = currentSettings.friendshipStartDateMillis

                calculateFriendshipDuration(startDateMillis)
                calculateBirthdayCountdown()
                delay(1000)
            }
        }
    }

    private fun calculateFriendshipDuration(startMillis: Long) {
        val now = System.currentTimeMillis()
        val diffMillis = (now - startMillis).coerceAtLeast(0)

        val calStart = Calendar.getInstance().apply { timeInMillis = startMillis }
        val calNow = Calendar.getInstance().apply { timeInMillis = now }

        var years = calNow.get(Calendar.YEAR) - calStart.get(Calendar.YEAR)
        var months = calNow.get(Calendar.MONTH) - calStart.get(Calendar.MONTH)
        var days = calNow.get(Calendar.DAY_OF_MONTH) - calStart.get(Calendar.DAY_OF_MONTH)

        if (days < 0) {
            months -= 1
            val prevMonth = Calendar.getInstance().apply {
                timeInMillis = now
                add(Calendar.MONTH, -1)
            }
            days += prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        if (months < 0) {
            years -= 1
            months += 12
        }

        val totalSeconds = diffMillis / 1000
        val totalDays = totalSeconds / (24 * 3600)
        val hours = (totalSeconds % (24 * 3600)) / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        _friendshipDuration.value = FriendshipDuration(
            years = years.coerceAtLeast(0),
            months = months.coerceAtLeast(0),
            days = days.coerceAtLeast(0),
            hours = hours.toInt(),
            minutes = minutes.toInt(),
            seconds = seconds.toInt(),
            totalDays = totalDays
        )
    }

    private fun calculateBirthdayCountdown() {
        val now = Calendar.getInstance(TimeZone.getDefault())
        val birthdayThisYear = Calendar.getInstance(TimeZone.getDefault()).apply {
            set(Calendar.MONTH, Calendar.AUGUST)
            set(Calendar.DAY_OF_MONTH, 18)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val isToday = now.get(Calendar.MONTH) == Calendar.AUGUST && now.get(Calendar.DAY_OF_MONTH) == 18

        val targetBirthday = if (now.after(birthdayThisYear) && !isToday) {
            Calendar.getInstance(TimeZone.getDefault()).apply {
                set(Calendar.YEAR, now.get(Calendar.YEAR) + 1)
                set(Calendar.MONTH, Calendar.AUGUST)
                set(Calendar.DAY_OF_MONTH, 18)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        } else {
            birthdayThisYear
        }

        val diffMillis = targetBirthday.timeInMillis - now.timeInMillis
        val totalSeconds = (diffMillis / 1000).coerceAtLeast(0)

        val days = totalSeconds / (24 * 3600)
        val hours = (totalSeconds % (24 * 3600)) / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        _birthdayCountdown.value = BirthdayCountdown(
            days = days,
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            isBirthdayToday = isToday
        )
    }

    fun addMemory(title: String, note: String, dateText: String, emojiIcon: String, category: String, photoUri: String? = null) {
        viewModelScope.launch {
            repository.addMemory(
                MemoryEntity(
                    title = title,
                    note = note,
                    dateText = dateText.ifBlank { "Ajke" },
                    emojiIcon = emojiIcon,
                    category = category,
                    photoUri = photoUri
                )
            )
        }
    }

    fun deleteMemory(memory: MemoryEntity) {
        viewModelScope.launch {
            repository.deleteMemory(memory)
        }
    }

    fun updateArafatMessage(message: String) {
        viewModelScope.launch {
            val curr = settings.value
            repository.saveSettings(curr.copy(arafatMessageToSani = message))
        }
    }

    fun updateSaniMessage(message: String) {
        viewModelScope.launch {
            val curr = settings.value
            repository.saveSettings(curr.copy(saniMessageToArafat = message))
        }
    }

    fun updateBirthdayWish(wish: String) {
        viewModelScope.launch {
            val curr = settings.value
            repository.saveSettings(curr.copy(arafatWishText = wish))
        }
    }

    fun votePoll(pollId: String, choiceIsSani: Boolean) {
        viewModelScope.launch {
            val curr = settings.value
            val updated = when (pollId) {
                "pagol" -> if (choiceIsSani) curr.copy(votePagolSani = curr.votePagolSani + 1) else curr.copy(votePagolArafat = curr.votePagolArafat + 1)
                "rag" -> if (choiceIsSani) curr.copy(voteRagSani = curr.voteRagSani + 1) else curr.copy(voteRagArafat = curr.voteRagArafat + 1)
                "sorry" -> if (choiceIsSani) curr.copy(voteSorrySani = curr.voteSorrySani + 1) else curr.copy(voteSorryArafat = curr.voteSorryArafat + 1)
                "moja" -> if (choiceIsSani) curr.copy(voteMojaSani = curr.voteMojaSani + 1) else curr.copy(voteMojaArafat = curr.voteMojaArafat + 1)
                else -> curr
            }
            repository.saveSettings(updated)
        }
    }

    fun toggleFutureGoal(goal: FutureGoalEntity) {
        viewModelScope.launch {
            repository.updateFutureGoal(goal.copy(isCompleted = !goal.isCompleted))
        }
    }

    fun addFutureGoal(title: String, iconEmoji: String) {
        viewModelScope.launch {
            repository.addFutureGoal(FutureGoalEntity(title = title, iconEmoji = iconEmoji))
        }
    }

    fun deleteFutureGoal(goal: FutureGoalEntity) {
        viewModelScope.launch {
            repository.deleteFutureGoal(goal)
        }
    }

    fun setDarkModeOption(option: String) {
        viewModelScope.launch {
            val curr = settings.value
            repository.saveSettings(curr.copy(darkModeOption = option))
        }
    }

    fun toggleParticles(enabled: Boolean) {
        viewModelScope.launch {
            val curr = settings.value
            repository.saveSettings(curr.copy(particlesEnabled = enabled))
        }
    }

    fun setFriendshipStartDate(millis: Long) {
        viewModelScope.launch {
            val curr = settings.value
            repository.saveSettings(curr.copy(friendshipStartDateMillis = millis))
            calculateFriendshipDuration(millis)
        }
    }

    fun nextFunnyMessage() {
        val messages = FriendshipDataDefaults.funnyMessages
        val next = messages.random()
        _currentFunnyMessage.value = next
    }

    fun nextRandomQuestion() {
        val questions = FriendshipDataDefaults.randomQuestions
        val next = questions.random()
        _currentRandomQuestion.value = next
    }

    fun nextRandomQuote() {
        val quotes = FriendshipDataDefaults.banglishQuotes
        val next = quotes.random()
        _currentRandomQuote.value = next
    }

    fun triggerCelebration() {
        viewModelScope.launch {
            _celebrationActive.value = true
            delay(5000)
            _celebrationActive.value = false
        }
    }
}
