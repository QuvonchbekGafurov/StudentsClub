package com.example.studentclubs.admin.bottomscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studentclubs.students.data.EventViewModel
import com.example.studentclubs.students.data.ProfileViewModel
import com.example.studentclubs.students.data.event.EventEntity
import com.example.studentclubs.students.data.profile.ProfileEntity

@Composable
fun NotificationScreen(
    eventViewModel: EventViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    // Profile va Eventlarni olish
    val profile by profileViewModel.profile.collectAsState()
    val events by eventViewModel.allEvents.collectAsState(emptyList())

    LazyColumn {

        // Events
        items(events) { event ->
            if (event.participant == 2) {
                EventItem(event = event, profile)
            }
        }
    }
}



@Composable
fun EventItem(event: EventEntity, profile: ProfileEntity?, modifier:Modifier= Modifier.padding(16.dp)) {
    val eventViewModel: EventViewModel = hiltViewModel()
    var participantState by remember { mutableStateOf(event.participant) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Blue)
            .padding(16.dp)
    ) {
        if (profile!==null) {
            Text(text = "${profile.name} ${profile.lastname} qatnashmoqchi ")
        }
        Row {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Add icon",
                tint = Color.Red,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        participantState = 0 // UI ni yangilash
                        eventViewModel.updateParticipant(
                            event,
                            0
                        ) // ViewModel orqali DB ni yangilash
                    })
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Add icon",
                tint = Color.Green,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        participantState = 1// UI ni yangilash
                        eventViewModel.updateParticipant(
                            event,
                            1
                        ) // ViewModel orqali DB ni yangilash
                    })
        }
        androidx.compose.material3.Text(
            text = "${event.category}  ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        androidx.compose.material3.Text(text = "Tashkilotchi : ${event.leader}", fontSize = 14.sp)
        androidx.compose.material3.Text(text = "date : ${event.activeDate}", fontSize = 14.sp)
        androidx.compose.material3.Text(text = "manzil : ${event.loc}", fontSize = 14.sp)
        androidx.compose.material3.Text(
            text = "Tavsif:",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        androidx.compose.material3.Text(text = event.description, fontSize = 16.sp)
    }
}
