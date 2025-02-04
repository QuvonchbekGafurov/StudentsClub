package com.example.studentclubs.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
@Database(entities = [EventEntity::class, ProfileEntity::class], version = 3, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun profileDao(): ProfileDao

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
                    .addMigrations(MIGRATION_1_2)  // Avvalgi migration
                    .addMigrations(MIGRATION_2_3)  // Yangi ProfileEntity uchun migration
                    .fallbackToDestructiveMigration()  // Agar eski ma'lumotlar kerak bo'lmasa
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Avvalgi Migration (1 → 2)
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

        // Yangi ProfileEntity Migration (2 → 3)
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `profiles` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`name` TEXT NOT NULL, " +
                            "`lastname` TEXT NOT NULL, " +
                            "`username` TEXT NOT NULL, " +
                            "`password` TEXT NOT NULL, " +
                            "`imageUrl` TEXT, " +
                            "`nomer` TEXT NOT NULL)"
                )
            }
        }
    }
}
