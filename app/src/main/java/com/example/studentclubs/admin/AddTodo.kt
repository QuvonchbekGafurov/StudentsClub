package com.example.studentclubs.admin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.studentclubs.students.data.EventViewModel
import com.example.studentclubs.students.data.event.EventDao
import com.example.studentclubs.students.data.event.EventEntity
import com.example.studentclubs.ui.theme.mainbackgroun
import java.util.Calendar

@Composable
fun AddTodo(eventViewModel: EventViewModel, navController: NavController) {
    var name by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Kategoriya tanlang") }  // Default text
    var loc by remember { mutableStateOf("") }
    var leader by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf("") }
    var participant by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("Sana tanlanmagan") }
    var selectedTime by remember { mutableStateOf("Vaqt tanlanmagan") }

    val context = LocalContext.current

    val categories = listOf(
        "Barchasi", "Sport", "San'at va Madaniyat", "Tadbirlar va Festivallar",
        "Ilmiy Klublar", "Ijtimoiy Xizmatlar", "Biznes va Tadbirkorlik", "Sog'liqni Saqlash va Fitnes"
    )

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainbackgroun)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        // **Kategoriya (DropdownMenu)**
        Text(text = "Category")
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { expanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                    .background(mainbackgroun)
                    .padding(16.dp)
            ) {
                Text(category)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat) },
                        onClick = {
                            category = cat
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **Ism**
        Text(text = "Name")
        BasicTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                .background(mainbackgroun)
                .padding(16.dp),
            decorationBox = { innerTextField ->
                if (name.isEmpty()) Text("Ism kiriting", color = Color.Gray)
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // **Sarlavha**
        Text(text = "Title")
        BasicTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                .background(mainbackgroun)
                .padding(16.dp),
            decorationBox = { innerTextField ->
                if (title.isEmpty()) Text("Sarlavha kiriting", color = Color.Gray)
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // **Joylashuv (Location)**
        Text(text = "Location")
        BasicTextField(
            value = loc,
            onValueChange = { loc = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                .background(mainbackgroun)
                .padding(16.dp),
            decorationBox = { innerTextField ->
                if (loc.isEmpty()) Text("Joylashuv kiriting", color = Color.Gray)
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // **Lider**
        Text(text = "Leader")
        BasicTextField(
            value = leader,
            onValueChange = { leader = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                .background(mainbackgroun)
                .padding(16.dp),
            decorationBox = { innerTextField ->
                if (leader.isEmpty()) Text("Lider kiriting", color = Color.Gray)
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // **Rasm (Photo)**
        Text(text = "Photo URL")
        BasicTextField(
            value = photo,
            onValueChange = { photo = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                .background(mainbackgroun)
                .padding(16.dp),
            decorationBox = { innerTextField ->
                if (photo.isEmpty()) Text("Rasm URL kiriting", color = Color.Gray)
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // **Sana tanlash**
        Button(onClick = {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                },
                year,
                month,
                day
            ).show()
        }) {
            Text(selectedDate)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // **Vaqt tanlash**
        Button(onClick = {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(
                context,
                { _, selectedHour, selectedMinute ->
                    selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                },
                hour,
                minute,
                true
            ).show()
        }) {
            Text(selectedTime)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **Qo'shish tugmasi**
        Button(
            onClick = {
                if (name.isNotBlank() && title.isNotBlank() && category.isNotBlank() &&
                    loc.isNotBlank() && leader.isNotBlank() &&
                    selectedDate != "Sana tanlanmagan" && selectedTime != "Vaqt tanlanmagan"
                ) {
                    val todo = EventEntity(
                        activeDate = "$selectedDate $selectedTime",
                        name = name,
                        description = title,
                        loc = loc,
                        leader = leader,
                        photo = if (photo.isBlank()) null else photo,
                        participant = 0,
                        category = category
                    )

                    Log.e("TAG", "AddTodo: $todo")
                    eventViewModel.insertEvent(todo)
                    navController.navigate("main")

                } else {
                    Toast.makeText(
                        context,
                        "Iltimos, barcha maydonlarni to'ldiring!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Qo'shish")
        }
    }
}
