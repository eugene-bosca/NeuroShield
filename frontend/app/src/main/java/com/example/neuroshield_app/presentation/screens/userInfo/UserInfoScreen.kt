package com.example.neuroshield_app.presentation.screens.userInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreen(onClickHomePage: () -> Unit) {
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
                label = { Text("First Name") },
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
                label = { Text("Last Name") },
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
                label = { Text("Team Name") },
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
                label = { Text("Coach Name") },
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF84BBD3),
                    focusedLabelColor = Color(0xFF84BBD3)
                )
            )
            Row(){
            }
        }
    }
}
