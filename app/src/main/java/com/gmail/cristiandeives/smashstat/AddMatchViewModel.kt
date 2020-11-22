package com.gmail.cristiandeives.smashstat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.cristiandeives.smashstat.data.Fighter
import com.gmail.cristiandeives.smashstat.data.Match
import com.gmail.cristiandeives.smashstat.data.Repository
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddMatchViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = Repository.getInstance(app)

    private val _addMatchState = MutableLiveData<Resource<*>>()
    val addMatchState: LiveData<Resource<*>> = _addMatchState

    val date = MutableLiveData<LocalDate>().apply {
        value = LocalDate.now()
    }
    private fun dateValue() = (date.value ?: LocalDate.now())

    var myCharacter = Fighter.MARIO
    var theirCharacter = Fighter.MARIO
    var theirNickname = ""

    var elite: Int? = null
    private fun eliteValue() = when (elite) {
        R.id.elite_yes -> true
        R.id.elite_no -> false
        else -> null
    }

    var rematch: Int? = null
    private fun rematchValue() = when (rematch) {
        R.id.rematch_yes -> true
        R.id.rematch_no -> false
        else -> null
    }

    var winner: Int? = null
    private fun winnerValue() = when (winner) {
        R.id.winner_self -> Match.Player.SELF
        R.id.winner_other -> Match.Player.THE_OTHER
        else -> null
    }

    val givenDamageText = MutableLiveData<String>()
    private fun givenDamageValue() = givenDamageText.value?.toIntOrNull()

    val takenDamageText = MutableLiveData<String>()
    private fun takenDamageValue() = takenDamageText.value?.toIntOrNull()

    val newGspText = MutableLiveData<String>()
    private fun newGspValue() = newGspText.value?.toIntOrNull()

    var disconnected: Int? = null
    private fun disconnectedValue() = when (disconnected) {
        R.id.disconnected_self -> Match.Player.SELF
        R.id.disconnected_other -> Match.Player.THE_OTHER
        else -> null
    }

    var selfDeath: Int? = null
    private fun selfDeathValue() = when (selfDeath) {
        R.id.self_death_self -> Match.Player.SELF
        R.id.self_death_other -> Match.Player.THE_OTHER
        else -> null
    }

    var teabagging: Int? = null
    private fun teabaggingValue() = when (teabagging) {
        R.id.teabagging_self -> Match.Player.SELF
        R.id.teabagging_other -> Match.Player.THE_OTHER
        else -> null
    }

    var lag: Int? = null
    private fun lagValue() = when (lag) {
        R.id.lag_yes -> true
        R.id.lag_no -> false
        else -> null
    }

    var funMatch: Int? = null
    private fun funValue() = when (funMatch) {
        R.id.fun_yes -> true
        R.id.fun_no -> false
        else -> null
    }

    fun addMatch() {
        val match = Match(
            date = dateValue(),
            myCharacter = myCharacter,
            theirCharacter = theirCharacter,
            theirNickname = theirNickname,
            isEliteSmash = eliteValue(),
            isRematch = rematchValue(),
            winner = winnerValue(),
            givenDamage = givenDamageValue(),
            takenDamage = takenDamageValue(),
            newGsp = newGspValue(),
            disconnected = disconnectedValue(),
            selfDeath = selfDeathValue(),
            teabagging = teabaggingValue(),
            lag = lagValue(),
            funMatch = funValue(),
        )

        viewModelScope.launch {
            try {
                Log.d(TAG, "adding match $match...")
                _addMatchState.value = Resource.Loading<Unit>()

                repo.createMatch(match)

                Log.d(TAG, "match added successfully")
                _addMatchState.value = Resource.Success<Unit>()
            } catch (ex: Exception) {
                Log.e(TAG, "failed to add match [${ex.message}", ex)
                _addMatchState.value = Resource.Error<Unit>(ex)
            }
        }
    }

    companion object {
        private val TAG = AddMatchViewModel::class.java.simpleName
    }
}