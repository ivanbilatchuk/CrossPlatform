package com.example.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DividerScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Divider") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Horizontal Dividers", style = MaterialTheme.typography.titleMedium)

            Text("Item 1", style = MaterialTheme.typography.bodyLarge)
            HorizontalDivider()
            Text("Item 2", style = MaterialTheme.typography.bodyLarge)
            HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
            Text("Item 3", style = MaterialTheme.typography.bodyLarge)
            HorizontalDivider(thickness = 4.dp, color = MaterialTheme.colorScheme.secondary)
            Text("Item 4 (after thick divider)", style = MaterialTheme.typography.bodyLarge)

            Spacer(Modifier.height(8.dp))
            Text("Vertical Dividers", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Left", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
                VerticalDivider()
                Text("Middle", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
                VerticalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                Text("Right", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(Modifier.height(8.dp))
            Text("List Items", style = MaterialTheme.typography.titleMedium)
            listOf("Apple", "Banana", "Cherry", "Date", "Elderberry").forEach { item ->
                ListItem(headlineContent = { Text(item) })
                HorizontalDivider()
            }
        }
    }
}
