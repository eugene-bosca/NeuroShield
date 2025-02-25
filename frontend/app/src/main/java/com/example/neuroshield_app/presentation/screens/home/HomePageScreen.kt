package com.example.neuroshield_app.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neuroshield_app.R


@Composable
fun HomePageScreen(onClickHistory: () -> Unit, onClickUserInfo: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.neuroshield_wide),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Adjusts the image to cover the entire background
        )
        Image(
            painter = painterResource(id = R.drawable.neuroshield_logo),
            contentDescription = "neuroshield",
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(10.dp)
                .size(200.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.be_your_best),
                contentDescription = "Centered image",
                modifier = Modifier.size(400.dp)
                    .offset(y = 200.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(300.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(innerPadding)
            ) {
                ElevatedButton(onClick = onClickUserInfo,
                        modifier = Modifier
                        .width(200.dp)
                        .height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3))
                ) {
                    Text("Test",
                        fontSize = 20.sp
                    )
                }
                ElevatedButton(onClick = onClickHistory,
                        modifier = Modifier
                        .width(200.dp)
                        .height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE6F7FF))
                ) {
                    Text("Results",
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}