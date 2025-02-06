package com.example.studentclubs.admin.bottomscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studentclubs.R
import com.example.studentclubs.admin.ProfileImagePicker
import com.example.studentclubs.students.data.EventViewModel
import com.example.studentclubs.students.data.event.EventEntity
import com.example.studentclubs.ui.theme.mainbackgroun

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(eventViewModel: EventViewModel, navController: NavController) {
    val allEvents by eventViewModel.allEvents.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }

    val filteredEvents = allEvents.filter { event ->
        event.category.contains(searchQuery, ignoreCase = true) ||
                event.description.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainbackgroun)
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search events") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Upcoming Events", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredEvents) { event ->
                EventItem(event, eventViewModel, navController)
            }
        }
    }
}

@Composable
fun EventItem(event: EventEntity, eventViewModel: EventViewModel, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
            .background(Color.Transparent)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).height(200.dp)) {
            Row {
                Text(text = "${event.category}", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${event.leader}", fontSize = 14.sp, color = Color.Gray)
            }
            Row {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "",
                    contentScale = ContentScale.Crop ,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .weight(1f)

                )
                Text(
                    text = event.description, fontSize = 16.sp, modifier = Modifier.weight(1f),
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }

        Row {

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { eventViewModel.deleteEventById(event.id) },
                tint = Color(0xFFD7B600)
            )
        }
    }
}
