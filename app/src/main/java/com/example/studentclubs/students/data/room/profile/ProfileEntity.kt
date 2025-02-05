package com.example.studentclubs.students.data.room.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val lastname: String,
    val username: String,
    val password: String, // Hashlangan parol
    val imageUrl: String?,
    val nomer: String
)
