package com.example.studentclubs.splashscreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studentclubs.loadEventsFromJson
import com.example.studentclubs.students.data.EventViewModel
import com.example.studentclubs.students.data.ProfileViewModel
import com.example.studentclubs.toEventEntity
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: ProfileViewModel= hiltViewModel()
    val eventViewModel: EventViewModel= hiltViewModel()

    // Observe the LiveData and convert it to State
    val profile by viewModel.profile.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadProfile()
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
    val context= LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
    val isFirstLaunch = remember { sharedPreferences.getBoolean("first_launch", true) }

    if (isFirstLaunch) {
        val eventsFromJson = loadEventsFromJson(context)
        LaunchedEffect(Unit) {
            eventsFromJson.forEach { event ->
                val eventEntity = event.toEventEntity()
                eventViewModel.insertEvent(eventEntity)
            }
            // Birinchi marta ishga tushganini belgilash
            sharedPreferences.edit().putBoolean("first_launch", false).apply()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Loading...", style = MaterialTheme.typography.headlineLarge)
    }
}