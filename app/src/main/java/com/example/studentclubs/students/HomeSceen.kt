package com.example.studentclubs.students


import android.content.Context
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studentclubs.R
import com.example.studentclubs.loadEventsFromJson
import com.example.studentclubs.students.data.EventViewModel
import com.example.studentclubs.students.data.event.EventEntity
import com.example.studentclubs.toEventEntity
import com.example.studentclubs.ui.theme.maincolor
import javax.annotation.meta.When

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val eventViewModel: EventViewModel = hiltViewModel()

    // Eventlarni observer qilish
    val events by eventViewModel.allEvents.collectAsState(emptyList())


    val jobCategories = listOf(
        "Barchasi", "Sport", "San'at va Madaniyat", "Tadbirlar va Festivallar",
        "Ilmiy Klublar", "Ijtimoiy Xizmatlar", "Biznes va Tadbirkorlik", "Sog'liqni Saqlash va Fitnes"
    )

    val selectedCategory = remember { mutableStateOf("Barchasi") } // Tanlangan filter
    val selectedEvent = remember { mutableStateOf<EventEntity?>(null) } // Tanlangan event
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) } // BottomSheet ochiq yoki yo'qligini nazorat qilish uchun

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 10.dp)) {
        // LazyRow orqali filter qilish
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(jobCategories) { category ->
                val isSelected = selectedCategory.value == category
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(if (isSelected) Color.Blue else Color.LightGray)
                        .clickable { selectedCategory.value = category }
                        .padding(10.dp)
                ) {
                    Text(
                        text = category,
                        color = Color.White,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Filterlangan eventlar ro'yxati
        val filteredEvents = remember(selectedCategory.value, events) {
            if (selectedCategory.value == "Barchasi") {
                events
            } else {
                events.filter { it.category == selectedCategory.value }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredEvents) { event ->
                JobItem(
                    jobTitle = event,
                    onClick = {
                        selectedEvent.value = event
                        isSheetOpen = true
                    }
                )
            }
        }
    }

    // **Bottom Sheet** - tanlangan eventni ko'rsatish
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
            sheetState = sheetState
        ) {
            selectedEvent.value?.let { event ->
                BottomSheetContent(event)
            }
        }
    }
}

@Composable
fun JobItem(jobTitle: EventEntity, onClick: () -> Unit) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Blue)
                .clickable {
                    onClick()
                }
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "",
                    contentScale = ContentScale.Crop ,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)

                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "${jobTitle.category}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    )
                    Text(text = "${jobTitle.description}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row (modifier = Modifier.fillMaxWidth()){
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = "${jobTitle.loc}")
                    }
                    Text(text = "${jobTitle.activeDate}", color = Color.Gray)
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }

        }

    Spacer(modifier = Modifier.height(20.dp))
}
@Composable
fun BottomSheetContent(event: EventEntity) {
    val eventViewModel: EventViewModel = hiltViewModel()

    // participant qiymatini lokal state sifatida saqlash
    var participantState by remember { mutableStateOf(event.participant) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row {
            val statusText = when (participantState) {
                0 -> "Clubga qo'shiling"
                1 -> "Siz Clubga a'zosiz"
                2 -> "Kutilmoqda"
                else -> "Noma'lum holat"
            }

            Text(
                text = statusText,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .background(Color.Transparent, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                when (participantState) {
                    0 -> Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add icon",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                participantState = 2 // UI ni yangilash
                                eventViewModel.updateParticipant(event, 2) // ViewModel orqali DB ni yangilash
                            }
                    )

                    1 -> Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Check icon",
                        tint = Color.Green,
                        modifier = Modifier.size(30.dp)
                    )

                    2 -> Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Timer icon",
                        tint = maincolor,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        Text(text = "${event.category}  ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Tashkilotchi : ${event.leader}", fontSize = 14.sp)
        Text(text = "date : ${event.activeDate}", fontSize = 14.sp)
        Text(text = "manzil : ${event.loc}", fontSize = 14.sp)
        Text(text = "Tavsif:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Text(text = event.description, fontSize = 16.sp)
    }
}
