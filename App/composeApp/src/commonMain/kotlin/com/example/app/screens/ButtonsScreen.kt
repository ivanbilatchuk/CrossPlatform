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
fun ButtonsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buttons") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var clickedButton by remember { mutableStateOf("None") }

            Text("Last clicked: $clickedButton", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(8.dp))

            Button(onClick = { clickedButton = "Button" }, modifier = Modifier.fillMaxWidth()) {
                Text("Button")
            }
            OutlinedButton(onClick = { clickedButton = "OutlinedButton" }, modifier = Modifier.fillMaxWidth()) {
                Text("Outlined Button")
            }
            TextButton(onClick = { clickedButton = "TextButton" }, modifier = Modifier.fillMaxWidth()) {
                Text("Text Button")
            }
            ElevatedButton(onClick = { clickedButton = "ElevatedButton" }, modifier = Modifier.fillMaxWidth()) {
                Text("Elevated Button")
            }
            FilledTonalButton(onClick = { clickedButton = "FilledTonalButton" }, modifier = Modifier.fillMaxWidth()) {
                Text("Filled Tonal Button")
            }
            Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
                Text("Disabled Button")
            }
        }
    }
}
