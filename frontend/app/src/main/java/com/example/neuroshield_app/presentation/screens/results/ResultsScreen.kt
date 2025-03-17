package com.example.neuroshield_app.presentation.screens.results

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.neuroshield_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    patientId: String,
    onClickHomePage: () -> Unit,
    resultsViewModel: ResultsViewModel = hiltViewModel()
) {

    val user by resultsViewModel.user.collectAsState()
    val isLoading by resultsViewModel.isLoading.collectAsState()
    val errorMessage by resultsViewModel.errorMessage.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    // This state controls whether the right-side drawer is visible
    var showDrawer by remember { mutableStateOf(false) }
    val tabTitles = listOf("Smooth Pursuit", "Pupil Light Reflex")

    LaunchedEffect(true) {
        resultsViewModel.fetchUser(patientId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                LargeTopAppBar(
                    title = {
                        Text("Results for ${user?.first_name} ${user?.last_name}")
                    },
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
                        // Toggle the drawer visibility when the menu icon is tapped
                        IconButton(onClick = { showDrawer = true }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                tint = Color(0xFF84BBD3),
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.error_24px),
                        tint = Color(0xFFFAAD14),
                        contentDescription = "Error",
                        modifier = Modifier
                            .size(250.dp)
                            .padding(bottom = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFFF5D1), shape = RoundedCornerShape(20.dp))
                            .border(1.dp, Color(0xFFFAAD14), shape = RoundedCornerShape(20.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Medical Attention Recommended",
                            color = Color(0xFFFFA500),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = Modifier.height(75.dp))

                    TabRow(
                        selectedTabIndex = selectedTab,
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .background(Color(0xFFEAF5FF), shape = RoundedCornerShape(20.dp)),
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                color = Color(0xFF84BBD3)
                            )
                        }
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                modifier = Modifier.padding(8.dp),
                                selectedContentColor = Color(0xFF84BBD3),
                                unselectedContentColor = Color.Black.copy(alpha = 0.6f)
                            ) {
                                Text(
                                    text = title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    when (selectedTab) {
                        0 -> Text(
                            "Smooth Pursuit details go here.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )

                        1 -> Text(
                            "Pupil Light Reflex details go here.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        )
        // Right-side drawer overlay
        if (showDrawer) {
            Box(modifier = Modifier.fillMaxSize()
                .padding(top = 25.dp)
            ) {
                // Semi-transparent overlay to dismiss the drawer on tap.
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { showDrawer = false }
                )
                // Animated drawer sliding in from the right.
                AnimatedVisibility(
                    visible = showDrawer,
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(400.dp)
                            .align(Alignment.CenterEnd),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Header with title and close button.
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Patient Details",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                IconButton(onClick = { showDrawer = false }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Close Drawer"
                                    )
                                }
                            }
                            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                            // Patient icon.
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Patient",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .background(Color.Transparent)
                                    .padding(8.dp)
                                    .size(50.dp)
                            )
                            Card(
                                modifier = Modifier.padding(16.dp),
                                shape = RoundedCornerShape(20.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F7FF))
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    PatientInfoRow(
                                        label = "First Name",
                                        value = user?.first_name ?: "N/A"
                                    )
                                    PatientInfoRow(
                                        label = "Last Name",
                                        value = user?.last_name ?: "N/A"
                                    )
                                    PatientInfoRow(
                                        label = "Patient ID",
                                        value = user?.patient_id ?: "N/A"
                                    )
                                    PatientInfoRow(
                                        label = "Team Name",
                                        value = user?.team_name ?: "N/A"
                                    )
                                    PatientInfoRow(
                                        label = "Coach Name",
                                        value = user?.coach_name ?: "N/A"
                                    )
                                    PatientInfoRow(
                                        label = "Date of Hit",
                                        value = user?.date_of_hit ?: "N/A"
                                    )
                                    PatientInfoRow(
                                        label = "Time of Hit",
                                        value = user?.time_of_hit ?: "N/A"
                                    )
                                    PatientInfoRow(
                                        label = "Record Creation",
                                        value = user?.created_at ?: "N/A"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun PatientInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$label:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, fontSize = 16.sp)
    }
}