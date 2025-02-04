package com.example.studentclubs.screen


import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studentclubs.data.EventViewModel
import com.example.studentclubs.navigation.NavBarBody
import com.example.studentclubs.navigation.NavBarHeader
import com.example.studentclubs.navigation.NavigationItem
import com.example.studentclubs.navigation.Screens
import com.example.studentclubs.navigation.SetUpNavGraph
import com.example.studentclubs.ui.theme.StudentClubsTheme
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val eventViewModel: EventViewModel = hiltViewModel()
    val events by eventViewModel.allEvents.observeAsState(emptyList())


    val systemTheme = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(systemTheme) }
    val items = listOf(
        NavigationItem("Home", Screens.Home.route, Icons.Filled.Home, Icons.Outlined.Home),
        NavigationItem(
            "Profile",
            Screens.Profile.route,
            Icons.Filled.Person,
            Icons.Outlined.Person
        ),
        NavigationItem(
            "Notification",
            Screens.Notification.route,
            Icons.Filled.Notifications,
            Icons.Outlined.Notifications,
            9
        ),
        NavigationItem(
            "Setting",
            Screens.Setting.route,
            Icons.Filled.Settings,
            Icons.Outlined.Settings
        ),
        NavigationItem("Share", "share", Icons.Filled.Share, Icons.Outlined.Share),
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val topBarTitle = items.find { it.route == currentRoute }?.title ?: items[0].title

    ModalNavigationDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                NavBarHeader()
                Spacer(modifier = Modifier.height(8.dp))
                NavBarBody(items, currentRoute) { selectedItem ->
                    if (selectedItem.route == "share") {
                        Toast.makeText(context, "Share Clicked", Toast.LENGTH_LONG).show()
                    } else {
                        navController.navigate(selectedItem.route) {
                            popUpTo(
                                navController.graph.startDestinationRoute ?: return@navigate
                            ) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    scope.launch { drawerState.close() }
                }
            }
        },
        drawerState = drawerState
    ) {
        StudentClubsTheme(useDarkTheme = isDarkTheme) { // <-- Theme
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(topBarTitle) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "menu")
                            }
                        },
                        actions = {
                            Row {
                                ThemeToggleButton(
                                    checked = isDarkTheme,
                                    onCheckedChange = { isDarkTheme = !isDarkTheme })
                            }
                        }
                    )
                }
            ) { innerPadding ->
                SetUpNavGraph(navController, innerPadding)
            }
        }
    }
}

@Composable
fun ThemeToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val backgroundColor = if (checked) Color(0xFFF5C16C) else Color(0xFFD9D2C1)
    val iconColor = if (checked) Color(0xFF3E2E1C) else Color(0xFF6B5E52)

    Surface(
        modifier = Modifier
            .size(60.dp, 32.dp)
            .clip(CircleShape)
            .clickable { onCheckedChange(checked) },
        color = backgroundColor,
        tonalElevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (!checked) Arrangement.Start else Arrangement.End,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(iconColor)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (checked) Icons.Default.NightlightRound else Icons.Default.WbSunny,
                    contentDescription = "Toggle Icon",
                    tint = Color.White
                )
            }
        }
    }
}
