package com.example.studentclubs.students.data.event

import java.util.concurrent.Flow
import javax.inject.Inject

class EventRepository @Inject constructor(private val eventDao: EventDao) {

    fun getAllEvents(): kotlinx.coroutines.flow.Flow<List<EventEntity>> = eventDao.getAllEvents()

    fun getEventById(eventId: Int): kotlinx.coroutines.flow.Flow<EventEntity?> = eventDao.getEventById(eventId)

    suspend fun insertEvent(event: EventEntity) {
        eventDao.insertEvent(event)
    }

    suspend fun updateEvent(event: EventEntity) {
        eventDao.updateEvent(event)
    }

    suspend fun updateEventById(eventId: Int, title: String, description: String, activeDate: Long) {
        eventDao.updateEventById(eventId, title, description, activeDate)
    }

    suspend fun deleteAllEvents() {
        eventDao.deleteAllEvents()
    }

    suspend fun deleteEventById(eventId: Int) {
        eventDao.deleteEventById(eventId)
    }
}
