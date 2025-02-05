package com.example.studentclubs.students.data


import com.example.studentclubs.students.data.room.profile.ProfileDao
import com.example.studentclubs.students.data.room.profile.ProfileEntity
import javax.inject.Inject
import javax.inject.Singleton
class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {

    // Profilni olish
    suspend fun getProfile(): ProfileEntity? {
        return profileDao.getProfile()
    }

    // Yangi profilni saqlash
    suspend fun saveProfile(profile: ProfileEntity) {
        profileDao.insertProfile(profile)
    }

    // Profilni yangilash
    suspend fun updateProfile(profile: ProfileEntity) {
        profileDao.updateProfile(profile)
    }
}
