package com.example.neuroshield_app.presentation.screens.history

import androidx.compose.foundation.border
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class PersonStatus(
    val name: String,
    val status: String,
    val isAttentionRecommended: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryPageScreen(onClickHomePage: () -> Unit) {
    // Example data
    val people = listOf(
        PersonStatus("Andrew Smith", "Medical Attention Recommended", true),
        PersonStatus("Noah Baker", "Cleared", false),
        PersonStatus("Jordan Brown", "Cleared", false),
        PersonStatus("Emma Peel", "Medical Attention Recommended", true),
        PersonStatus("Becky Peel", "Medical Attention Recommended", true)
    )

    // Search query state
    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Results") },
                navigationIcon = {
                    IconButton(onClick = onClickHomePage ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color(0xFF84BBD3),
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            tint = Color(0xFF84BBD3),
                            contentDescription = "Localized description"
                        )
                    }
                },
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
                onSearch = {
                    // Handle the search action here (e.g., perform filtering)
                    searchActive = false
                },
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

            // Filter the list by search query if needed
            val filteredPeople = people.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }

            // List of results
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                items(filteredPeople) { person ->
                    val statusColor = if (person.isAttentionRecommended) Color.Red else Color(0xFF4CAF50)
                    ListItem(
                        headlineContent = { Text(person.name) },
                        supportingContent = { Text(
                            text = person.status,
                            color = statusColor,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.border(width = 1.dp, color = statusColor, shape = RoundedCornerShape(20.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        )
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
                                Text(text = "Edit",
                                    color = Color(0xFF84BBD3)
                                )
                            }

                        }
                    )
                    Divider()
                }
            }
        }
    }
}
