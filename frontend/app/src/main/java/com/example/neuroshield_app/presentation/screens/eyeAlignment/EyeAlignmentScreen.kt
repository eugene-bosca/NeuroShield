package com.example.neuroshield_app.presentation.screens.eyeAlignment

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.neuroshield_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EyeAlignmentScreen(
    onClickUserInfoPage: () -> Unit,
    onClickRunTestsPage: () -> Unit
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Eye Alignment") },
                navigationIcon = {
                    IconButton(onClick = onClickUserInfoPage) {
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
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = "Please align the players pupil with the eye outline as best as possible.",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                color = Color.Gray
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.padding(80.dp))

            CameraAlignmentStream()

            Spacer(modifier = Modifier.height(100.dp))

            ElevatedButton(
                onClick = onClickRunTestsPage,
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3))
            ) {
                Text(
                    "Continue",
                    fontSize = 20.sp
                )
            }
            ElevatedButton(
                onClick = onClickUserInfoPage,
                modifier = Modifier.fillMaxWidth(0.90f)
                    .padding(bottom = 20.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE6F7FF))
            ) {
                Text(
                    "Back",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
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

@Composable
fun CameraAlignmentStream() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Left Eye",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                text = "Right Eye",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(300.dp),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    webChromeClient = WebChromeClient()
                    webViewClient = WebViewClient()
                    loadUrl("http://192.168.0.105:5000/camera_alignment")
                }
            }
        )
    }
}

