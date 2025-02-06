package com.example.studentclubs.students.data.event

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [EventEntity::class], version = 3, exportSchema = false)
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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // 2 dan 3 ga o‘tish migrationi qo‘shildi
                    .fallbackToDestructiveMigration()  // Eski ma'lumotlar kerak bo‘lmasa, buzilsa yo‘q qilib yangi yaratadi
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Migration 1 dan 2 ga
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
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

                database.execSQL("INSERT INTO events_new SELECT * FROM events")
                database.execSQL("DROP TABLE events")
                database.execSQL("ALTER TABLE events_new RENAME TO events")
            }
        }

        // Migration 2 dan 3 ga (id ustunini autoGenerate qilish uchun yangi jadval yaratamiz)
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `events_new` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + // AUTO_INCREMENT qo‘shildi
                        "`category` TEXT NOT NULL," +
                        "`name` TEXT NOT NULL," +
                        "`description` TEXT NOT NULL," +
                        "`activeDate` TEXT NOT NULL," +
                        "`loc` TEXT NOT NULL," +
                        "`leader` TEXT NOT NULL," +
                        "`photo` TEXT," +
                        "`participant` INTEGER NOT NULL)")

                database.execSQL("INSERT INTO events_new (id, category, name, description, activeDate, loc, leader, photo, participant) SELECT id, category, name, description, activeDate, loc, leader, photo, participant FROM events")

                database.execSQL("DROP TABLE events")
                database.execSQL("ALTER TABLE events_new RENAME TO events")
            }
        }
    }
}
