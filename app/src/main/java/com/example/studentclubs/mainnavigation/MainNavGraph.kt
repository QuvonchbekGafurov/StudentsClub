package com.example.studentclubs.mainnavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studentclubs.splashscreen.SplashScreen
import com.example.studentclubs.admin.AdminScreen
import com.example.studentclubs.register.screen.LoginScreen
import com.example.studentclubs.register.screen.RegisterScreen
import com.example.studentclubs.students.StudentScreen
import com.example.studentclubs.students.data.EventViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController = navController) }
        composable("login") { LoginScreen(navController = navController) }
        composable("admin") {
            val viewModel: EventViewModel = hiltViewModel()
            AdminScreen(viewModel=viewModel, navControllerTOP =navController) }
        composable("student") { StudentScreen(navControllerTOP =navController) }
        composable("register"){ RegisterScreen(navController=navController) }
    }
}