package com.example.neuroshield_app.presentation.screens.userInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.neuroshield_app.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreen(onClickHomePage: () -> Unit, onClickEyeAlignment: () -> Unit) {
    val firstNameState = remember { mutableStateOf("") }
    val lastNameState = remember { mutableStateOf("") }
    val teamNameState = remember { mutableStateOf("") }
    val coachNameState = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Patient Information") },
                navigationIcon = {
                    IconButton(onClick = onClickHomePage) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color(0xFF84BBD3),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            tint = Color(0xFF84BBD3),
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = firstNameState.value,
                onValueChange = { firstNameState.value = it },
                label = { Text("First Name", color = Color(0xFF84BBD3)) },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF84BBD3),
                    focusedLabelColor = Color(0xFF84BBD3)
                )
            )
            OutlinedTextField(
                value = lastNameState.value,
                onValueChange = { lastNameState.value = it },
                label = { Text("Last Name", color = Color(0xFF84BBD3)) },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF84BBD3),
                    focusedLabelColor = Color(0xFF84BBD3)
                )
            )
            OutlinedTextField(
                value = teamNameState.value,
                onValueChange = { teamNameState.value = it },
                label = { Text("Team Name", color = Color(0xFF84BBD3)) },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF84BBD3),
                    focusedLabelColor = Color(0xFF84BBD3)
                )
            )
            OutlinedTextField(
                value = coachNameState.value,
                onValueChange = { coachNameState.value = it },
                label = { Text("Coach Name", color = Color(0xFF84BBD3)) },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF84BBD3),
                    focusedLabelColor = Color(0xFF84BBD3)
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 20.dp, start = 40.dp, end = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                DatePickerDocked(modifier = Modifier.weight(1f))
                TimePickerDocked(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(20.dp))
            ElevatedButton(onClick = onClickEyeAlignment,
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3))
            ) {
                Text("Continue",
                    fontSize = 20.sp
                )
            }
            ElevatedButton(onClick = onClickHomePage,
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE6F7FF))
            ) {
                Text("Cancel",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
            Box(Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter) {
                Image(
                    painter = painterResource(id = R.drawable.neuroshield_logo),
                    contentDescription = "neuroshield",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(150.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(modifier: Modifier = Modifier) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = modifier) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Date of Hit", color = Color(0xFF84BBD3)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF84BBD3),
                focusedLabelColor = Color(0xFF84BBD3)
            )
        )

        if (showDatePicker) {
            Dialog(
                onDismissRequest = { showDatePicker = false },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDocked(modifier: Modifier = Modifier) {
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    var selectedTime by remember { mutableStateOf("") }

    Box(
        modifier = modifier) {
        OutlinedTextField(
            value = selectedTime,
            onValueChange = { },
            label = { Text("Time of Hit", color = Color(0xFF84BBD3)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showTimePicker = !showTimePicker }) {
                    Icon(
                        painter = painterResource(id = R.drawable.time_icon),
                        contentDescription = "Select time"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF84BBD3),
                focusedLabelColor = Color(0xFF84BBD3)
            )
        )

        if (showTimePicker) {
            TimePickerDialog(
                onDismiss = { showTimePicker = false },
                onConfirm = {
                    selectedTime = "${timePickerState.hour}:${timePickerState.minute}"
                    showTimePicker = false
                }
            ) {
                TimeInput(
                    state = timePickerState,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}
