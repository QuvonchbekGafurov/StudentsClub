package com.example.studentclubs.students.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studentclubs.students.HomeScreen
import com.example.studentclubs.students.ProfileScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    navControllerTop: NavController
) {
    NavHost(navController = navController,
        startDestination = Screens.Home.route){
        composable(Screens.Home.route){
            HomeScreen(innerPadding = innerPadding)
        }

        composable(Screens.Profile.route){
            ProfileScreen(navController=navControllerTop,innerPadding = innerPadding)
        }

    }
}