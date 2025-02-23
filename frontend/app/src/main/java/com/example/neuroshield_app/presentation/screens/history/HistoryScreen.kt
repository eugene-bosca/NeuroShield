package com.example.neuroshield_app.presentation.screens.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HistoryPageScreen(onClickHomePage: () -> Unit) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            Text("Profile!")
            Button(onClick = onClickHomePage) {
                Text("Go to HomePage")
            }
        }
    }
}
