package com.example.studentclubs.students.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentclubs.students.data.profile.ProfileEntity
import com.example.studentclubs.students.data.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<ProfileEntity?>(null)
    val profile: StateFlow<ProfileEntity?> = _profile.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _profile.value = profileRepository.getProfile()
        }
    }

    fun saveProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            profileRepository.saveProfile(profile)
            _profile.value = profile
        }
    }

    fun updateProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            profileRepository.updateProfile(profile)
            _profile.value = profile
        }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            profileRepository.deleteProfile()
            _profile.value = null
        }
    }
}
