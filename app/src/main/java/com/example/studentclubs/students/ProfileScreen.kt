package com.example.studentclubs.students


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studentclubs.students.data.ProfileViewModel
import com.example.studentclubs.students.data.room.profile.ProfileEntity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val profile by viewModel.profile.observeAsState()

    var name by remember { mutableStateOf(profile?.name ?: "") }
    var lastname by remember { mutableStateOf(profile?.lastname ?: "") }
    var username by remember { mutableStateOf(profile?.username ?: "") }
    var password by remember { mutableStateOf(profile?.password ?: "") }
    var nomer by remember { mutableStateOf(profile?.nomer ?: "") }
    var imageUrl by remember { mutableStateOf(profile?.imageUrl ?: "") }

    LaunchedEffect(profile) {
        if (profile == null) {
            viewModel.getProfile() // Profilni olish
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Text("Edit Profile")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lastname,
            onValueChange = { lastname = it },
            label = { Text("Lastname") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = nomer,
            onValueChange = { nomer = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedProfile = ProfileEntity(
                    id = profile?.id ?: 0,
                    name = name,
                    lastname = lastname,
                    username = username,
                    password = password,
                    imageUrl = imageUrl,
                    nomer = nomer
                )

                // Logga tahrirlangan ma'lumotlarni chiqarish
                Log.d("ProfileChanges", "Updated Profile: $updatedProfile")

                viewModel.updateProfile(updatedProfile)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
