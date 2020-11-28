package com.gmail.cristiandeives.smashstat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gmail.cristiandeives.smashstat.data.Repository

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = Repository.getInstance(app)

    val matches = repository.readAllMatches()
}