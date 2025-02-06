package com.example.studentclubs.students.data.profile

import androidx.room.*

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles LIMIT 1")
    suspend fun getProfile(): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile:ProfileEntity)

    @Update
    suspend fun updateProfile(profile:ProfileEntity)

    @Query("DELETE FROM profiles")
    suspend fun deleteProfile()
}

