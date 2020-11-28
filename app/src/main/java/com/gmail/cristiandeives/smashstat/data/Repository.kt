package com.gmail.cristiandeives.smashstat.data

import android.content.Context
import androidx.room.Room

class Repository private constructor(context: Context) {
    private val db = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    suspend fun createMatch(match: Match) {
        db.matchDao().create(match)
    }

    fun readAllMatches() = db.matchDao().readAll()

    companion object {
        private const val DATABASE_NAME = "smashstat"

        private var instance: Repository? = null

        fun getInstance(context: Context) = instance ?: Repository(context.applicationContext).also {
            instance = it
        }
    }
}