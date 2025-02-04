package com.example.studentclubs.data.room

import androidx.room.*
import java.util.concurrent.Flow

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Update
    suspend fun updateProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profiles LIMIT 1")
    suspend fun getProfile(): ProfileEntity?

    @Delete
    suspend fun deleteProfile(profile: ProfileEntity)
}
