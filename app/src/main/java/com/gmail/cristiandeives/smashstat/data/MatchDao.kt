package com.gmail.cristiandeives.smashstat.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MatchDao {
    @Insert
    suspend fun create(match: Match)
}