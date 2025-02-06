package com.example.studentclubs.admin

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.studentclubs.admin.bottomscreen.NotificationScreen
import com.example.studentclubs.admin.bottomscreen.ProfileScreen
import com.example.studentclubs.admin.bottomscreen.TodoScreen
import com.example.studentclubs.students.data.EventViewModel
import com.example.studentclubs.ui.theme.bottomnavigation
import com.example.studentclubs.ui.theme.maincolor
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomAppBarWithFab(todoViewModel: EventViewModel, navController: NavController,navControllerTOP: NavController) {
    val selectedItem = remember { mutableStateOf("home") }
    Scaffold(
        content = {
            when (selectedItem.value) {
                "home" -> TodoScreen(todoViewModel, navController)
                "notification" -> NotificationScreen()
                "profile" -> ProfileScreen(navControllerTOP)
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("addtodo")
                },
                shape = RoundedCornerShape(50),
                backgroundColor = maincolor
            ) {
                Icon(Icons.Filled.Add, tint = Color.White, contentDescription = "Add")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,

        bottomBar = {
            BottomAppBar(
                backgroundColor = bottomnavigation,
                cutoutShape = RoundedCornerShape(50),
                content = {
                    BottomNavigation(backgroundColor = bottomnavigation) {

                        BottomNavigationItem(
                            selected = selectedItem.value == "notification",
                            onClick = { selectedItem.value = "notification" },
                            icon = { Icon(Icons.Outlined.Notifications, contentDescription = "notification") },
                            label = { Text(text = "Notification") },
                            alwaysShowLabel = false,
                            selectedContentColor = Color.Black
                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == "profile",
                            onClick = { selectedItem.value = "profile" },
                            icon = { Icon(Icons.Default.Person, contentDescription = "profile") },
                            label = { Text(text = "profile") },
                            alwaysShowLabel = false,
                            selectedContentColor = Color.Black
                        )
                    }
                }
            )
        }
    )
}
