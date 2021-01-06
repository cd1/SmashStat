package com.gmail.cristiandeives.smashstat

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AddMatchViewModel(app: Application) : SaveMatchViewModel(app) {
    override fun saveMatch() {
        val match = matchValue()

        viewModelScope.launch {
            try {
                Log.d(TAG, "adding match $match...")
                _saveMatchState.value = Resource.Loading<Unit>()

                repo.createMatch(match)

                Log.d(TAG, "match added successfully")
                _saveMatchState.value = Resource.Success<Unit>()
            } catch (ex: Exception) {
                Log.e(TAG, "failed to add match [${ex.message}", ex)
                _saveMatchState.value = Resource.Error<Unit>(ex)
            }
        }
    }

    companion object {
        private val TAG = AddMatchViewModel::class.java.simpleName
    }
}