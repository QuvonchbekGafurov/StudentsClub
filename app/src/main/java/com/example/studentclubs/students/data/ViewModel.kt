package com.example.studentclubs.students.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.studentclubs.students.data.room.event.EventEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    // Barcha events
    val allEvents: LiveData<List<EventEntity>> = eventRepository.getAllEvents()
        .asLiveData()

    // Eventni qo'shish yoki yangilash
    fun insertOrUpdateEvent(event: EventEntity) {
        viewModelScope.launch {
            eventRepository.insertOrUpdateEvent(event)
        }
    }

    // Eventni yangilash
    fun updateEvent(event: EventEntity) {
        viewModelScope.launch {
            eventRepository.updateEvent(event)
        }
    }

    // Barcha eventlarni o'chirish
    fun deleteAllEvents() {
        viewModelScope.launch {
            eventRepository.deleteAllEvents()
        }
    }
}
