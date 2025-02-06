package com.example.studentclubs.students.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.studentclubs.students.data.event.EventEntity
import com.example.studentclubs.students.data.event.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    // Barcha eventlarni olish uchun StateFlow
    private val _allEvents = MutableStateFlow<List<EventEntity>>(emptyList())
    val allEvents: StateFlow<List<EventEntity>> = _allEvents.asStateFlow()

    // Qidiruv so‘rovi
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        getAllEvents()
    }

    // Barcha eventlarni yuklash
    private fun getAllEvents() {
        viewModelScope.launch {
            eventRepository.getAllEvents()
                .collect { events ->
                    _allEvents.value = events
                }
        }
    }

    // Qidiruv so‘rovini yangilash
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Event qo‘shish
    fun insertEvent(event: EventEntity) {
        viewModelScope.launch {
            eventRepository.insertEvent(event)
        }
    }

    // Eventni ID bo‘yicha o‘chirish
    fun deleteEventById(eventId: Int) {
        viewModelScope.launch {
            eventRepository.deleteEventById(eventId)
        }
    }

    // Eventni ID bo‘yicha yangilash
    fun updateEventById(eventId: Int, title: String, description: String, activeDate: Long) {
        viewModelScope.launch {
            eventRepository.updateEventById(eventId, title, description, activeDate)
        }
    }

    // Eventni ID bo‘yicha olish
    fun getEventById(eventId: Int): Flow<EventEntity?> {
        return eventRepository.getEventById(eventId)
    }

    // Ishtirokchi holatini yangilash
    fun updateParticipant(event: EventEntity, participant: Int) {
        viewModelScope.launch {
            val updatedEvent = event.copy(participant = participant)
            eventRepository.updateEvent(updatedEvent)
        }
    }
}
