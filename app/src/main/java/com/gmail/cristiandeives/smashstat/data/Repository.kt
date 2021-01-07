package com.gmail.cristiandeives.smashstat.data

import android.content.Context
import androidx.room.Room
import java.time.LocalDateTime

class Repository private constructor(context: Context) {
    private val db = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .addMigrations(AppDatabase.MIGRATION_1_2)
        .build()

    suspend fun createMatch(match: Match) {
        db.matchDao().create(match.copy(createdAt = LocalDateTime.now()))
    }

    fun readAllMatches() = db.matchDao().readAll()

    suspend fun readMatch(id: Int) = db.matchDao().read(id)

    suspend fun updateMatch(match: Match) {
        db.matchDao().update(match)
    }

    suspend fun deleteMatch(id: Int) {
        db.matchDao().delete(id)
    }

    companion object {
        private const val DATABASE_NAME = "smashstat"

        private var instance: Repository? = null

        fun getInstance(context: Context) = instance ?: Repository(context.applicationContext).also {
            instance = it
        }
    }
}