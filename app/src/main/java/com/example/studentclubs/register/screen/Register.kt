package com.example.studentclubs.register.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studentclubs.R
import com.example.studentclubs.register.utils.MyTextField
import com.example.studentclubs.students.data.ProfileViewModel
import com.example.studentclubs.students.data.room.profile.ProfileEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier,navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val viewModel: ProfileViewModel = hiltViewModel()
    val profile by viewModel.profile.observeAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.register),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Text(
            text = "Register",
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp
        )

        MyTextField(
            value = email,
            onValueChange = { email = it },
            hint = "Email",
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.fillMaxWidth()
        )

        MyTextField(
            value = name,
            onValueChange = { name = it },
            hint = "Name",
            leadingIcon = Icons.Outlined.AccountCircle,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth()
        )

        MyTextField(
            value = password,
            onValueChange = { password = it },
            hint = "Password",
            leadingIcon = Icons.Outlined.Lock,
            keyboardType = KeyboardType.Password,
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                Log.d("RegisterScreen", "Register clicked with Email: $email, Name: $name, Password: $password")
                Log.d("RegisterScreen", "Login Register")
                if(email.isNotEmpty()&& name.isNotEmpty() && password.isNotEmpty()){
                    val newUser = ProfileEntity(username = email, name = name, password = password, nomer = "", lastname = "", imageUrl = "")
                    viewModel.saveProfile(newUser)
                    navController.navigate("login")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Register",
                fontSize = 17.sp,
                modifier = Modifier.padding(vertical = 8.dp)

            )
        }

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Already have an account? ",
                fontSize = 16.sp,
            )
            Text(
                text = "Login",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    Log.d("RegisterScreen", "Login clicked")
                    navController.navigate("login")
                }
            )
        }
    }
}