package com.gmail.cristiandeives.smashstat.data

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Match::class], version = 2)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao

    companion object {
        private val TAG = AppDatabase::class.java.simpleName

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d(TAG, "migrating database [$startVersion -> $endVersion]...")

                database.execSQL("ALTER TABLE `Match` ADD COLUMN `createdAt` INTEGER NOT NULL DEFAULT 0")
                database.execSQL("UPDATE `Match` SET `createdAt` = `date`")

                Log.d(TAG, "migration successful [$startVersion -> $endVersion]")
            }
        }
    }
}