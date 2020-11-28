package com.gmail.cristiandeives.smashstat.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MatchDao {
    @Insert
    suspend fun create(match: Match)

    @Query("SELECT * FROM `Match` ORDER BY date DESC, createdAt DESC")
    fun readAll(): LiveData<List<Match>>
}