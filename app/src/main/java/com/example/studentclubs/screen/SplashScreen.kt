package com.example.studentclubs.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studentclubs.students.data.ProfileViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: ProfileViewModel= hiltViewModel()

    // Observe the LiveData and convert it to State
    val profile by viewModel.profile.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.getProfile()
        delay(3000)
        Log.e("TAG", "SplashScreen: $profile", )
        if (profile == null) {
            navController.navigate("login")
        } else if (profile?.name == "Quvonchbek" && profile?.password == "Gafurov") {
            navController.navigate("admin")
        } else {
            navController.navigate("student")
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Loading...", style = MaterialTheme.typography.headlineLarge)
    }
}