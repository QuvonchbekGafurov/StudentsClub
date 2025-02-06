package com.example.studentclubs.students.data.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.util.concurrent.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event:EventEntity)

    @Query("SELECT * FROM events ORDER BY activeDate ASC")
    fun getAllEvents(): kotlinx.coroutines.flow.Flow<List<EventEntity>>

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()

    @Update
    suspend fun updateEvent(event:EventEntity)

    // ID bo‘yicha o‘chirish
    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun deleteEventById(eventId: Int)

    // ID bo‘yicha yangilash
    @Query("UPDATE events SET category = :title, description = :description, activeDate = :activeDate WHERE id = :eventId")
    suspend fun updateEventById(eventId: Int, title: String, description: String, activeDate: Long)

    @Query("SELECT * FROM events WHERE id = :eventId LIMIT 1")
    fun getEventById(eventId: Int): kotlinx.coroutines.flow.Flow<EventEntity?>


}
