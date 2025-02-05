package com.example.studentclubs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.navigation.compose.rememberNavController
import com.example.studentclubs.mainnavigation.MainNavigationGraph

import com.example.studentclubs.ui.theme.StudentClubsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentClubsTheme {
                val navController = rememberNavController()
                MainNavigationGraph(navController)
            }
        }
    }
}
