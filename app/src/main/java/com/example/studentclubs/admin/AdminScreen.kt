package com.example.studentclubs.admin

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentclubs.students.data.EventViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminScreen(viewModel: EventViewModel,navControllerTOP: NavHostController) {
    BackHandler {
        // Hech narsa qilmaslik, orqa tugmasi bosilganda hech narsa bajarilmaydi
    }
    val navController = rememberNavController() // NavController yaratish

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { BottomAppBarWithFab(navController = navController, todoViewModel = viewModel, navControllerTOP = navControllerTOP) }
        composable("addtodo") { backStackEntry ->
            var todoId = backStackEntry.arguments?.getString("todoId")
            AddTodo(navController = navController, eventViewModel = viewModel)
        }
    }
}