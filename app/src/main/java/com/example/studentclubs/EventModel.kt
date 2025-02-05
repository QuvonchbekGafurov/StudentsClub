package com.example.studentclubs

import android.content.Context
import com.example.studentclubs.students.data.room.event.EventEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Event(
    val id: Int ,
    val category: String,
    val name: String,
    val description: String,
    val activeDate: String,
    val loc: String,
    val leader: String,
    val photo: String?,
    val participant: Int
)

fun loadEventsFromJson(context: Context): List<Event> {
    val jsonString = context.resources.openRawResource(R.raw.events).bufferedReader().use { it.readText() }
    val gson = Gson()
    val listType = object : TypeToken<List<Event>>() {}.type
    return gson.fromJson(jsonString, listType)
}

fun Event.toEventEntity(): EventEntity {
    return EventEntity(
        id = this.id,
        category = this.category,
        name = this.name,
        description = this.description,
        activeDate = this.activeDate ?: "",  // Agar activeDate null bo'lsa, bo'sh string bilan almashtirish
        loc = this.loc,
        leader = this.leader,
        photo=this.photo,
        participant = this.participant
    )
}
