package com.example.studentclubs.students.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentclubs.students.data.room.profile.ProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profile = MutableLiveData<ProfileEntity?>()
    val profile: LiveData<ProfileEntity?> = _profile

    // Profilni olish
    fun getProfile() {
        viewModelScope.launch {
            _profile.value = profileRepository.getProfile()
        }
    }

    // Yangi profilni saqlash
    fun saveProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            profileRepository.saveProfile(profile)
        }
    }

    // Profilni yangilash
    fun updateProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            profileRepository.updateProfile(profile)
        }
    }
}
