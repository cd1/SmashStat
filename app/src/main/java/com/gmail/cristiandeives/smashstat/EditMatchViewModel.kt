package com.gmail.cristiandeives.smashstat

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.cristiandeives.smashstat.data.Match
import kotlinx.coroutines.launch

class EditMatchViewModel(app: Application, private val matchId: Int) : SaveMatchViewModel(app) {
    private val _readMatchState = MutableLiveData<Resource<*>>()
    val readMatchState: LiveData<Resource<*>> = _readMatchState

    init {
        readExistingMatch()
    }

    private fun readExistingMatch() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "reading match with ID=$matchId...")
                _readMatchState.value = Resource.Loading<Unit>()

                val existingMatch = repo.readMatch(matchId)
                existingMatch?.let { syncMatchData(it) }

                Log.d(TAG, "match read successfully")
                _readMatchState.value = Resource.Success(existingMatch)
            } catch (ex: Exception) {
                Log.d(TAG, "failed to read existing match [${ex.message}]", ex)
                _readMatchState.value = Resource.Error<Unit>(ex)
            }
        }
    }

    private fun syncMatchData(match: Match) {
        dateValue = match.date
        myCharacterValue = match.myCharacter
        theirCharacterValue = match.theirCharacter
        theirNicknameValue = match.theirNickname
        eliteValue = match.isEliteSmash
        rematchValue = match.isRematch
        winnerValue = match.winner
        givenDamageValue = match.givenDamage
        takenDamageValue = match.takenDamage
        newGspValue = match.newGsp
        disconnectedValue = match.disconnected
        selfDeathValue = match.selfDeath
        teabaggingValue = match.teabagging
        lagValue = match.lag
        funValue = match.funMatch
    }

    override fun saveMatch() {
        val match = matchValue().copy(id = matchId)

        viewModelScope.launch {
            try {
                Log.d(TAG, "editing match with ID=${match.id} $match...")
                _saveMatchState.value = Resource.Loading<Unit>()

                repo.updateMatch(match)

                Log.d(TAG, "match edited successfully")
                _saveMatchState.value = Resource.Success<Unit>()
            } catch (ex: Exception) {
                Log.e(TAG, "failed to edit match [${ex.message}", ex)
                _saveMatchState.value = Resource.Error<Unit>(ex)
            }
        }
    }

    companion object {
        private val TAG = EditMatchViewModel::class.java.simpleName
    }
}