package com.example.studentclubs.students.data.room.event

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: Int = 0,
    val category: String,
    val name: String,
    val description: String,
    val activeDate: String,
    val loc: String,
    val leader: String,
    val photo: String?,
    val participant: Int
)