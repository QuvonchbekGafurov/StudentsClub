package com.example.studentclubs.admin.bottomscreen

import android.provider.ContactsContract.Profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studentclubs.R
import com.example.studentclubs.students.data.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController){
    val viewModel: ProfileViewModel = hiltViewModel()
    Column (modifier = Modifier.padding(12.dp)){
        Row {
            Icon(
                Icons.Outlined.Logout,
                contentDescription = "Logout",
                modifier = Modifier.size(30.dp).clickable {
                    navController.navigate("login")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Log Out Profile", modifier = Modifier, fontSize = 12.sp)
        }
    }
}