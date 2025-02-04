package com.example.studentclubs.data

import android.content.Context
import androidx.room.Room
import com.example.studentclubs.data.room.EventDao
import com.example.studentclubs.data.room.EventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EventDatabase {
        return Room.databaseBuilder(
            context,
            EventDatabase::class.java,
            "events.db"
        ).build()
    }

    @Provides
    fun provideEventDao(database: EventDatabase): EventDao {
        return database.eventDao()
    }
}
