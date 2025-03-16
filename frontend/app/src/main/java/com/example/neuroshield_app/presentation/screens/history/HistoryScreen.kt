package com.example.neuroshield_app.presentation.screens.history

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryPageScreen(
    onClickHomePage: () -> Unit,
    onClickResultsPage: (patientId: String) -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    val users by historyViewModel.events.collectAsState()
    val isLoading by historyViewModel.isLoading.collectAsState()
    val errorMessage by historyViewModel.errorMessage.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        historyViewModel.fetchUsers()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Results") },
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
                    IconButton(onClick = { /* Open menu */ }) {
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
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { searchActive = false },
                active = searchActive,
                onActiveChange = { searchActive = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                },
                placeholder = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = SearchBarDefaults.colors(containerColor = Color(0xFFE6F7FF))
            ) {}

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF84BBD3))
                    }
                }
                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: $errorMessage",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        items(users) { user ->
                            // Wrap the ListItem with a clickable modifier to navigate
                            ListItem(
                                modifier = Modifier.clickable { onClickResultsPage(user.patient_id) },
                                headlineContent = { Text("${user.first_name} ${user.last_name}") },
                                supportingContent = {
                                    Column {
                                        Text(
                                            text = "Team: ${user.team_name}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = "Coach: ${user.coach_name}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = "Date of Hit: ${user.date_of_hit}",
                                            color = if (user.team_name.contains("Warriors", ignoreCase = true)) Color.Red else Color(0xFF4CAF50),
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier
                                                .border(
                                                    width = 1.dp,
                                                    color = if (user.team_name.contains("Warriors", ignoreCase = true)) Color.Red else Color(0xFF4CAF50),
                                                    shape = RoundedCornerShape(20.dp)
                                                )
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp)
                                    )
                                },
                                trailingContent = {
                                    TextButton(onClick = { /* Handle edit */ }) {
                                        Text(
                                            text = "Edit",
                                            color = Color(0xFF84BBD3)
                                        )
                                    }
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

