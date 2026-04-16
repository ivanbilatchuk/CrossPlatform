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
fun SwitchScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Switch") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var wifi by remember { mutableStateOf(true) }
            var bluetooth by remember { mutableStateOf(false) }
            var notifications by remember { mutableStateOf(true) }
            var darkMode by remember { mutableStateOf(false) }
            var location by remember { mutableStateOf(false) }

            Text("Settings", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            listOf(
                Triple("Wi-Fi", wifi) { v: Boolean -> wifi = v },
                Triple("Bluetooth", bluetooth) { v: Boolean -> bluetooth = v },
                Triple("Notifications", notifications) { v: Boolean -> notifications = v },
                Triple("Dark Mode", darkMode) { v: Boolean -> darkMode = v },
                Triple("Location", location) { v: Boolean -> location = v },
            ).forEach { (label, checked, onToggle) ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(label, style = MaterialTheme.typography.bodyLarge)
                        Switch(checked = checked, onCheckedChange = onToggle)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("Disabled Switch (always off):", style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.outline)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = false, onCheckedChange = {}, enabled = false)
                Spacer(Modifier.width(12.dp))
                Text("Airplane Mode (disabled)", style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}
