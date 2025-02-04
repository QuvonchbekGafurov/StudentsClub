package com.example.studentclubs.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
@Database(entities = [EventEntity::class], version = 2, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event_database"
                )
                    .addMigrations(MIGRATION_1_2) // Migration qo'shish
                    .fallbackToDestructiveMigration()  // Agar eski ma'lumotlar kerak bo'lmasa
                    .build()
                INSTANCE = instance
                instance
            }
        }
        // Migration 1 dan 2 ga
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Yangi jadvalni yaratish
                database.execSQL("CREATE TABLE IF NOT EXISTS `events_new` (" +
                        "`id` INTEGER PRIMARY KEY NOT NULL," +
                        "`category` TEXT NOT NULL," +
                        "`name` TEXT NOT NULL," +
                        "`description` TEXT NOT NULL," +
                        "`activeDate` TEXT NOT NULL," +
                        "`loc` TEXT NOT NULL," +
                        "`leader` TEXT NOT NULL," +
                        "`photo` TEXT," +
                        "`participant` INTEGER NOT NULL)")

                // Eski jadvaldan yangi jadvalga ma'lumotlarni ko'chirish
                database.execSQL("INSERT INTO events_new SELECT * FROM events")

                // Eski jadvalni o'chirish
                database.execSQL("DROP TABLE events")

                // Yangi jadvalni eski jadval nomi bilan almashtirish
                database.execSQL("ALTER TABLE events_new RENAME TO events")
            }
        }
    }


}
