package com.example.studentclubs.mainnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studentclubs.screen.SplashScreen
import com.example.studentclubs.admin.AdminScreen
import com.example.studentclubs.register.screen.LoginScreen
import com.example.studentclubs.register.screen.RegisterScreen
import com.example.studentclubs.students.StudentScreen

@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController = navController) }
        composable("login") { LoginScreen(navController = navController) }
        composable("admin") { AdminScreen() }
        composable("student") { StudentScreen() }
        composable("register"){ RegisterScreen(navController=navController) }
    }
}