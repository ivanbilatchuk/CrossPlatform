package com.example.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogScreen(navController: NavController) {
    var showAlert by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("No action yet") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dialog") },
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
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Alert Dialogs", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Last action:", style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline)
                    Text(result, style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(Modifier.height(8.dp))

            Button(onClick = { showAlert = true }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Info, null)
                Spacer(Modifier.width(8.dp))
                Text("Info Dialog")
            }
            OutlinedButton(onClick = { showConfirm = true }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Warning, null)
                Spacer(Modifier.width(8.dp))
                Text("Confirm Dialog")
            }
        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            icon = { Icon(Icons.Default.Info, null) },
            title = { Text("Information") },
            text = { Text("This is a simple informational dialog demonstrating Material 3 AlertDialog.") },
            confirmButton = {
                TextButton(onClick = { result = "Info: OK clicked"; showAlert = false }) { Text("OK") }
            }
        )
    }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            icon = { Icon(Icons.Default.Warning, tint = MaterialTheme.colorScheme.error, contentDescription = null) },
            title = { Text("Confirm Action") },
            text = { Text("Are you sure you want to proceed? This action cannot be undone.") },
            confirmButton = {
                Button(onClick = { result = "Confirmed!"; showConfirm = false }) { Text("Confirm") }
            },
            dismissButton = {
                OutlinedButton(onClick = { result = "Cancelled"; showConfirm = false }) { Text("Cancel") }
            }
        )
    }
}
