package com.example.studentclubs.students


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studentclubs.students.data.ProfileViewModel
import com.example.studentclubs.students.data.profile.ProfileEntity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), navController: NavController, innerPadding: PaddingValues) {
    val profileState by viewModel.profile.collectAsState()

    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }

    // Profil ma’lumotlari yuklanganda state-ni yangilash
    LaunchedEffect(profileState) {
        profileState?.let { profile ->
            name = profile.name
            lastName = profile.lastname
            username = profile.username
            phoneNumber = profile.nomer
            imageUrl = profile.imageUrl ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row {
            Text(text = "Log Out Profile", modifier = Modifier, fontSize = 12.sp)
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                Icons.Outlined.Logout,
                contentDescription = "Logout",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.navigate("login")
                    }
            )

        }
        ProfileTextField(
            value = name,
            onValueChange = { name = it },
            label = "Ism"
        )

        ProfileTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Familiya"
        )

        ProfileTextField(
            value = username,
            onValueChange = { username = it },
            label = "Foydalanuvchi nomi"
        )

        ProfileTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = "Telefon raqami"
        )

        ProfileTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = "Rasm URL"
        )

        Button(
            onClick = {
                val updatedProfile = ProfileEntity(
                    id = profileState?.id ?: 0, // Agar ID bo'lsa, saqlaydi, aks holda 0
                    name = name,
                    lastname = lastName,
                    username = username,
                    nomer = phoneNumber,
                    imageUrl = imageUrl ?: "",
                    password = "asddsad"
                )
                viewModel.updateProfile(updatedProfile)
                navController.navigate("student")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Save Changes")
        }
    }
}
@Composable
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String // label qo'shilgan
) {
    val placeholderColor = Color.Gray

    Column(modifier = Modifier.fillMaxWidth()) {
        // Har bir field ustida label matnini joylash
        Text(
            text = label,
            color = Color.Black,  // Matn rangi
            style = TextStyle(fontWeight = FontWeight.Bold), // Kengaytirilgan uslub
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Placeholder text ko'rinishida
            if (value.isEmpty()) {
                Text(
                    text = "Kiriting",
                    color = placeholderColor,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(color = Color.Black), // Text color
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    innerTextField() // Ta'kidlash: placeholder alohida ko'rsatiladi
                }
            )
        }
    }
}
