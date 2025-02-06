package com.example.studentclubs.students.data.profile

import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    suspend fun getProfile(): ProfileEntity? = profileDao.getProfile()

    suspend fun saveProfile(profile: ProfileEntity) {
        profileDao.saveProfile(profile)
    }

    suspend fun updateProfile(profile: ProfileEntity) {
        profileDao.updateProfile(profile)
    }

    suspend fun deleteProfile() {
        profileDao.deleteProfile()
    }
}
