package com.example.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressBarScreen(navController: NavController) {
    var progress by remember { mutableStateOf(0.3f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress Bar") },
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
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Determinate Linear
            Text("Linear – Determinate", style = MaterialTheme.typography.titleMedium)
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
            Text("${(progress * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { progress = (progress - 0.1f).coerceAtLeast(0f) }) { Text("- 10%") }
                Button(onClick = { progress = (progress + 0.1f).coerceAtMost(1f) }) { Text("+ 10%") }
            }

            HorizontalDivider()

            // Indeterminate Linear
            Text("Linear – Indeterminate", style = MaterialTheme.typography.titleMedium)
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

            HorizontalDivider()

            // Determinate Circular
            Text("Circular – Determinate", style = MaterialTheme.typography.titleMedium)
            CircularProgressIndicator(progress = { progress })
            Text("${(progress * 100).toInt()}%", style = MaterialTheme.typography.bodySmall)

            HorizontalDivider()

            // Indeterminate Circular
            Text("Circular – Indeterminate", style = MaterialTheme.typography.titleMedium)
            CircularProgressIndicator()
        }
    }
}
