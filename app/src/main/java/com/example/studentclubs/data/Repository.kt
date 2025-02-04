package com.example.studentclubs.data

import com.example.studentclubs.data.room.EventDao
import com.example.studentclubs.data.room.EventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventDao: EventDao
) {

    // Barcha eventlarni olish
    fun getAllEvents(): Flow<List<EventEntity>> = eventDao.getAllEvents()

    // Yangi event qo'shish yoki mavjudini yangilash
    suspend fun insertOrUpdateEvent(event: EventEntity) {
        eventDao.insertEvent(event)
    }

    // Eventni yangilash
    suspend fun updateEvent(event: EventEntity) {
        eventDao.updateEvent(event)
    }

    // Barcha eventlarni o'chirish
    suspend fun deleteAllEvents() {
        eventDao.deleteAllEvents()
    }
}
