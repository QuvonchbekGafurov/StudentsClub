package com.example.studentclubs.students.data.room.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Query("SELECT * FROM events ORDER BY activeDate ASC")
    fun getAllEvents(): kotlinx.coroutines.flow.Flow<List<EventEntity>>

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()

    @Update
    suspend fun updateEvent(event: EventEntity)
}
