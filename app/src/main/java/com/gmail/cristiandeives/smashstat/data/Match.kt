package com.gmail.cristiandeives.smashstat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: LocalDate?,
    val myCharacter: Fighter?,
    val theirCharacter: Fighter?,
    val theirNickname: String,
    val isEliteSmash: Boolean?,
    val isRematch: Boolean?,
    val winner: Player?,
    val givenDamage: Int?,
    val takenDamage: Int?,
    val newGsp: Int?,
    val disconnected: Player?,
    val selfDeath: Player?,
    val teabagging: Player?,
    val lag: Boolean?,
    val funMatch: Boolean?,
) {
    enum class Player {
        SELF, THE_OTHER
    }
}