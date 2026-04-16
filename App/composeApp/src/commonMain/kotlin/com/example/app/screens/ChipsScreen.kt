package com.example.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chips") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Assist Chips
            Text("Assist Chips", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text("Settings") },
                    leadingIcon = { Icon(Icons.Default.Settings, null, Modifier.size(18.dp)) })
                AssistChip(onClick = {}, label = { Text("Favourites") },
                    leadingIcon = { Icon(Icons.Default.Star, null, Modifier.size(18.dp)) })
            }

            // Filter Chips
            Text("Filter Chips", style = MaterialTheme.typography.titleMedium)
            var f1 by remember { mutableStateOf(true) }
            var f2 by remember { mutableStateOf(false) }
            var f3 by remember { mutableStateOf(true) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = f1, onClick = { f1 = !f1 }, label = { Text("Kotlin") },
                    leadingIcon = if (f1) {{ Icon(Icons.Default.Done, null, Modifier.size(18.dp)) }} else null)
                FilterChip(selected = f2, onClick = { f2 = !f2 }, label = { Text("Swift") },
                    leadingIcon = if (f2) {{ Icon(Icons.Default.Done, null, Modifier.size(18.dp)) }} else null)
                FilterChip(selected = f3, onClick = { f3 = !f3 }, label = { Text("Rust") },
                    leadingIcon = if (f3) {{ Icon(Icons.Default.Done, null, Modifier.size(18.dp)) }} else null)
            }

            // Input Chips
            Text("Input Chips", style = MaterialTheme.typography.titleMedium)
            val tags = remember { mutableStateListOf("Android", "iOS", "Desktop") }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                tags.toList().forEach { tag ->
                    InputChip(
                        selected = false,
                        onClick = { tags.remove(tag) },
                        label = { Text(tag) },
                        trailingIcon = { Text("✕", style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
            if (tags.isEmpty()) Text("All chips removed. Restart screen to reset.",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)

            // Suggestion Chips
            Text("Suggestion Chips", style = MaterialTheme.typography.titleMedium)
            var suggestion by remember { mutableStateOf("") }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Hello!", "How are you?", "Great!").forEach {
                    SuggestionChip(onClick = { suggestion = it }, label = { Text(it) })
                }
            }
            if (suggestion.isNotEmpty())
                Text("Selected: $suggestion", style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary)
        }
    }
}
