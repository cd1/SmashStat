package com.gmail.cristiandeives.smashstat.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

object RoomConverters {
    private const val EPSILON_SYMBOL = "e"

    @JvmStatic
    @TypeConverter
    fun fromLocalDate(date: LocalDate) =
        fromLocalDateTime(date.atStartOfDay())

    @JvmStatic
    @TypeConverter
    fun toLocalDate(dateMillis: Long): LocalDate =
        toLocalDateTime(dateMillis).toLocalDate()

    @JvmStatic
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime) = dateTime
        .atZone(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()

    @JvmStatic
    @TypeConverter
    fun toLocalDateTime(dateTimeMillis: Long): LocalDateTime = Instant.ofEpochMilli(dateTimeMillis)
        .atZone(ZoneOffset.UTC)
        .toLocalDateTime()

    @JvmStatic
    @TypeConverter
    fun fromFighter(fighter: Fighter?): String? {
        if (fighter == null) {
            return null
        }

        val composedId = StringBuilder(fighter.id.toString())
        if (fighter.isEpsilon) {
            composedId.append(EPSILON_SYMBOL)
        }

        return composedId.toString()
    }

    @JvmStatic
    @TypeConverter
    fun toFighter(composedId: String): Fighter? {
        val isEpsilon = composedId.endsWith(EPSILON_SYMBOL)
        val lastIdIndex = if (isEpsilon) composedId.length - 1 else composedId.length
        val id = composedId.substring(0, lastIdIndex).toIntOrNull()

        return Fighter.values().find { f ->
            f.id == id && f.isEpsilon == isEpsilon
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromPlayer(player: Match.Player?) = player?.name

    @JvmStatic
    @TypeConverter
    fun toPlayer(name: String?) = name?.let { Match.Player.valueOf(it) }
}