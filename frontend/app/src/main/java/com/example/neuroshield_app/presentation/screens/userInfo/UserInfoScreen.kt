package com.example.neuroshield_app.presentation.screens.userInfo

import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.neuroshield_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreen(
    onClickHomePage: () -> Unit,
    onClickEyeAlignment: () -> Unit,
    viewModel: UserInfoViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val teamName by viewModel.teamName.collectAsState()
    val coachName by viewModel.coachName.collectAsState()

    val firstNameError by viewModel.firstNameError.collectAsState()
    val lastNameError by viewModel.lastNameError.collectAsState()
    val teamNameError by viewModel.teamNameError.collectAsState()
    val coachNameError by viewModel.coachNameError.collectAsState()

    val isRequestSuccessful by viewModel.isRequestSuccessful.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(isRequestSuccessful) {
        isRequestSuccessful?.let { success ->
            if (success) {
                onClickEyeAlignment() // Navigate only if successful
            } else {
                Toast.makeText(context, "Error creating user. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // First Name
            OutlinedTextField(
                value = firstName,
                onValueChange = viewModel::onFirstNameChange,
                label = { Text("First Name", color = Color(0xFF84BBD3)) },
                isError = firstNameError != null,
                supportingText = firstNameError?.let { { Text(it, color = Color.Red) } },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF84BBD3),
                    focusedLabelColor = Color(0xFF84BBD3)
                )
            )

            // Last Name
            OutlinedTextField(
                value = lastName,
                onValueChange = viewModel::onLastNameChange,
                label = { Text("Last Name", color = Color(0xFF84BBD3)) },
                isError = lastNameError != null,
                supportingText = lastNameError?.let { { Text(it, color = Color.Red) } },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF84BBD3),
                    focusedLabelColor = Color(0xFF84BBD3)
                )
            )

            // Team Name
            OutlinedTextField(
                value = teamName,
                onValueChange = viewModel::onTeamNameChange,
                label = { Text("Team Name", color = Color(0xFF84BBD3)) },
                isError = teamNameError != null,
                supportingText = teamNameError?.let { { Text(it, color = Color.Red) } },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF84BBD3),
                    focusedLabelColor = Color(0xFF84BBD3)
                )
            )

            // Coach Name
            OutlinedTextField(
                value = coachName,
                onValueChange = viewModel::onCoachNameChange,
                label = { Text("Coach Name", color = Color(0xFF84BBD3)) },
                isError = coachNameError != null,
                supportingText = coachNameError?.let { { Text(it, color = Color.Red) } },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF84BBD3),
                    focusedLabelColor = Color(0xFF84BBD3)
                )
            )

            // Date and Time Pickers
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 20.dp, start = 40.dp, end = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DatePickerDocked(modifier = Modifier.weight(1f), viewModel = viewModel)
                TimePickerDocked(modifier = Modifier.weight(1f), viewModel = viewModel)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Continue Button
            ElevatedButton(
                onClick = { viewModel.onContinueClicked() },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3)),
                enabled = !isLoading // Disable while loading
            ) {
                if (isLoading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp).padding(end = 8.dp),
                            strokeWidth = 2.dp
                        )
                        Text("Loading...", fontSize = 20.sp, color = Color.White)
                    }
                } else {
                    Text("Continue", fontSize = 20.sp, color = Color.White)
                }
            }

            // Cancel Button
            ElevatedButton(
                onClick = onClickHomePage,
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE6F7FF))
            ) {
                Text("Cancel", color = Color.Black, fontSize = 20.sp)
            }

            // Logo
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.neuroshield_logo),
                    contentDescription = "neuroshield",
                    modifier = Modifier.size(150.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    modifier: Modifier = Modifier,
    viewModel: UserInfoViewModel = hiltViewModel()
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val dateError by viewModel.dateError.collectAsState()

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Date of Hit", color = Color(0xFF84BBD3)) },
            readOnly = true,
            isError = dateError != null,
            supportingText = dateError?.let { { Text(it, color = Color.Red) } },
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
                onDismissRequest = { showDatePicker = false }
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

                    LaunchedEffect(datePickerState.selectedDateMillis) {
                        datePickerState.selectedDateMillis?.let {
                            viewModel.onDateSelected(it)
                            showDatePicker = false // Close dialog after selection
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDocked(
    modifier: Modifier = Modifier,
    viewModel: UserInfoViewModel = hiltViewModel()
) {
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    val selectedTime by viewModel.selectedTime.collectAsState()
    val timeError by viewModel.timeError.collectAsState()

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedTime,
            onValueChange = { },
            label = { Text("Time of Hit", color = Color(0xFF84BBD3)) },
            readOnly = true,
            isError = timeError != null,
            supportingText = timeError?.let { { Text(it, color = Color.Red) } },
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
                    viewModel.onTimeSelected(timePickerState.hour, timePickerState.minute)
                    showTimePicker = false // Close dialog after selection
                }
            ) {
                TimeInput(state = timePickerState)
            }
        }
    }
}

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
