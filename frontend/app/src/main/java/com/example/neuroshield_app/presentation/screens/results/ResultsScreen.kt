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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.example.neuroshield_app.data.models.Plr
import com.example.neuroshield_app.data.models.SmoothPursuit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    patientId: String,
    onClickHomePage: () -> Unit,
    resultsViewModel: ResultsViewModel = hiltViewModel()
) {

    val userDetails by resultsViewModel.userDetails.collectAsState()
    val isLoading by resultsViewModel.isLoading.collectAsState()
    val errorMessage by resultsViewModel.errorMessage.collectAsState()

    val user = userDetails?.user
    val plrDetails = userDetails?.plr
    val spDetails = userDetails?.smooth_pursuit

    var selectedTab by remember { mutableIntStateOf(0) }
    var showDrawer by remember { mutableStateOf(false) }
    val tabTitles = listOf("Smooth Pursuit", "Pupil Light Reflex")

    LaunchedEffect(true) {
        resultsViewModel.fetchUser(patientId)
    }

    val hasOutOfRange =
        (spDetails?.let {
            listOf(
                // Left eye 180° test
                it.phase_lag_l_180 !in 0.1..0.3,
                it.mean_squared_error_l_180 !in 0.0..0.5,
                it.pearson_coefficient_l_180 !in 0.8..1.0,
                // Left eye 360° test
                it.phase_lag_l_360 !in 0.1..0.3,
                it.mean_squared_error_l_360 !in 0.0..0.5,
                it.pearson_coefficient_l_360 !in 0.8..1.0,
                // Right eye 180° test
                it.phase_lag_r_180 !in 0.1..0.3,
                it.mean_squared_error_r_180 !in 0.0..0.5,
                it.pearson_coefficient_r_180 !in 0.8..1.0,
                // Right eye 360° test
                it.phase_lag_r_360 !in 0.1..0.3,
                it.mean_squared_error_r_360 !in 0.0..0.5,
                it.pearson_coefficient_r_360 !in 0.8..1.0
            ).any { flag -> flag }
        } ?: false) ||
                (plrDetails?.let {
                    listOf(
                        // Left eye values
                        it.max_pupil_diam_l !in 4.0..6.0,
                        it.min_pupil_diam_l !in 2.0..3.5,
                        it.percent_contstriction_l !in 30.0..60.0,
                        it.peak_constriction_velocity_l !in 3.0..5.0,
                        it.average_constriction_velocity_l !in 2.0..4.0,
                        it.peak_dilation_velocity_l !in 2.0..4.0,
                        it.average_dilation_velocity_l !in 1.5..3.0,
                        it.time_to_redilation_l !in 0.5..1.5,
                        // Right eye values
                        it.max_pupil_diam_r !in 4.0..6.0,
                        it.min_pupil_diam_r !in 2.0..3.5,
                        it.percent_contstriction_r !in 30.0..60.0,
                        it.peak_constriction_velocity_r !in 3.0..5.0,
                        it.average_constriction_velocity_r !in 2.0..4.0,
                        it.peak_dilation_velocity_r !in 2.0..4.0,
                        it.average_dilation_velocity_r !in 1.5..3.0,
                        it.time_to_redilation_r !in 0.5..1.5
                    ).any { flag -> flag }
                } ?: false)

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
                    if (hasOutOfRange) {
                        // Display warning icon and message.
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
                    } else {
                        // Display green check mark and cleared message.
                        Icon(
                            imageVector = Icons.Filled.Check,
                            tint = Color.Green,
                            contentDescription = "Cleared",
                            modifier = Modifier
                                .size(250.dp)
                                .padding(bottom = 8.dp)
                        )

                        Box(
                            modifier = Modifier
                                .background(Color(0xFFE8FFEB), shape = RoundedCornerShape(20.dp))
                                .border(1.dp, Color.Green, shape = RoundedCornerShape(20.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Cleared",
                                color = Color.Green,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(75.dp))

                    // Tab row for switching between tests
                    TabRow(
                        selectedTabIndex = selectedTab,
                        modifier = Modifier
                            .fillMaxWidth(0.80f)
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

                    // Display the test details in a scrollable grid
                    Box(modifier = Modifier.fillMaxWidth(0.8f)) {
                        when (selectedTab) {
                            0 -> {
                                if (spDetails != null) {
                                    SmoothPursuitDetails(smoothPursuit = spDetails)
                                } else {
                                    Text("Smooth Pursuit details not available")
                                }
                            }
                            1 -> {
                                if (plrDetails != null) {
                                    PlrDetails(plr = plrDetails)
                                } else {
                                    Text("Pupil Light Reflex details not available")
                                }
                            }
                        }
                    }
                }
            }
        )
        // Right-side drawer overlay (unchanged)
        if (showDrawer) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 25.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { showDrawer = false }
                )
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

// A helper data class for our grid items.
data class DataPoint(
    val label: String,
    val value: Double,
    val min: Double,
    val max: Double
) {
    val normalRangeText: String
        get() = "$min - $max"
}

// Helper function to choose text color based on whether the value is within range.
fun getDisplayColor(value: Double, min: Double, max: Double): Color {
    return if (value in min..max) Color.Green else Color(0xFFFFA500) // Yellow (using a hex value)
}

@Composable
fun SmoothPursuitDetails(smoothPursuit: SmoothPursuit) {
    // Left eye 180° data points
    val leftDataPoints180 = listOf(
        DataPoint("Phase Lag", smoothPursuit.phase_lag_l_180, 0.1, 0.3),
        DataPoint("Mean Squared Error", smoothPursuit.mean_squared_error_l_180, 0.0, 0.5),
        DataPoint("Pearson Coefficient", smoothPursuit.pearson_coefficient_l_180, 0.8, 1.0)
    )
    // Left eye 360° data points
    val leftDataPoints360 = listOf(
        DataPoint("Phase Lag", smoothPursuit.phase_lag_l_360, 0.1, 0.3),
        DataPoint("Mean Squared Error", smoothPursuit.mean_squared_error_l_360, 0.0, 0.5),
        DataPoint("Pearson Coefficient", smoothPursuit.pearson_coefficient_l_360, 0.8, 1.0)
    )
    // Right eye 180° data points
    val rightDataPoints180 = listOf(
        DataPoint("Phase Lag", smoothPursuit.phase_lag_r_180, 0.1, 0.3),
        DataPoint("Mean Squared Error", smoothPursuit.mean_squared_error_r_180, 0.0, 0.5),
        DataPoint("Pearson Coefficient", smoothPursuit.pearson_coefficient_r_180, 0.8, 1.0)
    )
    // Right eye 360° data points
    val rightDataPoints360 = listOf(
        DataPoint("Phase Lag", smoothPursuit.phase_lag_r_360, 0.1, 0.3),
        DataPoint("Mean Squared Error", smoothPursuit.mean_squared_error_r_360, 0.0, 0.5),
        DataPoint("Pearson Coefficient", smoothPursuit.pearson_coefficient_r_360, 0.8, 1.0)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ------------------ LEFT EYE ------------------
        item {
            Text(
                text = "Left Eye",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        // 180° Test Title
        item {
            Text(
                text = "180° Test",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }
        // Left Eye 180° in 2-column rows, with leftover card fix
        items(leftDataPoints180.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { data ->
                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F7FF))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = data.label,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = data.value.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = getDisplayColor(data.value, data.min, data.max)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Normal: ${data.normalRangeText}",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                // If there's only one item in this row, add an empty box to preserve 2-column layout
                if (rowItems.size == 1) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }

        // 360° Test Title
        item {
            Text(
                text = "360° Test",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }
        // Left Eye 360° in 2-column rows, with leftover card fix
        items(leftDataPoints360.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { data ->
                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F7FF))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = data.label,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = data.value.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = getDisplayColor(data.value, data.min, data.max)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Normal: ${data.normalRangeText}",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                if (rowItems.size == 1) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }

        // ------------------ DIVIDER BETWEEN EYES ------------------
        item {
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Gray,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // ------------------ RIGHT EYE ------------------
        item {
            Text(
                text = "Right Eye",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // 180° Test Title
        item {
            Text(
                text = "180° Test",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
        }
        // Right Eye 180° in 2-column rows, with leftover card fix
        items(rightDataPoints180.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { data ->
                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F7FF))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = data.label,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = data.value.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = getDisplayColor(data.value, data.min, data.max)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Normal: ${data.normalRangeText}",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                if (rowItems.size == 1) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }

        // 360° Test Title
        item {
            Text(
                text = "360° Test",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }
        // Right Eye 360° in 2-column rows, with leftover card fix
        items(rightDataPoints360.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { data ->
                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F7FF))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = data.label,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = data.value.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = getDisplayColor(data.value, data.min, data.max)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Normal: ${data.normalRangeText}",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                if (rowItems.size == 1) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }

        // SPACER AT THE BOTTOM
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PlrDetails(plr: Plr) {
    // Define left eye data points
    val leftDataPoints = listOf(
        DataPoint("Max Pupil Diam", plr.max_pupil_diam_l, 4.0, 6.0),
        DataPoint("Min Pupil Diam", plr.min_pupil_diam_l, 2.0, 3.5),
        DataPoint("Percent Constriction", plr.percent_contstriction_l, 30.0, 60.0),
        DataPoint("Peak Constriction Velocity", plr.peak_constriction_velocity_l, 3.0, 5.0),
        DataPoint("Average Constriction Velocity", plr.average_constriction_velocity_l, 2.0, 4.0),
        DataPoint("Peak Dilation Velocity", plr.peak_dilation_velocity_l, 2.0, 4.0),
        DataPoint("Average Dilation Velocity", plr.average_dilation_velocity_l, 1.5, 3.0),
        DataPoint("Time to Redilation", plr.time_to_redilation_l, 0.5, 1.5)
    )

    // Define right eye data points
    val rightDataPoints = listOf(
        DataPoint("Max Pupil Diam", plr.max_pupil_diam_r, 4.0, 6.0),
        DataPoint("Min Pupil Diam", plr.min_pupil_diam_r, 2.0, 3.5),
        DataPoint("Percent Constriction", plr.percent_contstriction_r, 30.0, 60.0),
        DataPoint("Peak Constriction Velocity", plr.peak_constriction_velocity_r, 3.0, 5.0),
        DataPoint("Average Constriction Velocity", plr.average_constriction_velocity_r, 2.0, 4.0),
        DataPoint("Peak Dilation Velocity", plr.peak_dilation_velocity_r, 2.0, 4.0),
        DataPoint("Average Dilation Velocity", plr.average_dilation_velocity_r, 1.5, 3.0),
        DataPoint("Time to Redilation", plr.time_to_redilation_r, 0.5, 1.5)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Left Eye Section Header
        item {
            Text(
                text = "Left Eye",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        // Left Eye data points in 2-column rows
        items(leftDataPoints.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { data ->
                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F7FF))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = data.label,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = data.value.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = getDisplayColor(data.value, data.min, data.max)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Normal: ${data.normalRangeText}",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
        // Divider between Left and Right Eye Sections
        item {
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Gray,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        // Right Eye Section Header
        item {
            Text(
                text = "Right Eye",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
            )
        }
        // Right Eye data points in 2-column rows
        items(rightDataPoints.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { data ->
                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F7FF))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = data.label,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = data.value.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = getDisplayColor(data.value, data.min, data.max)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Normal: ${data.normalRangeText}",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
        // Spacer at the Bottom
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}



