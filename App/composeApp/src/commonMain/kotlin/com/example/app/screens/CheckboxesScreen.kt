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
fun CheckboxesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkboxes") },
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var option1 by remember { mutableStateOf(true) }
            var option2 by remember { mutableStateOf(false) }
            var option3 by remember { mutableStateOf(false) }
            var option4 by remember { mutableStateOf(true) }

            Text("Select options:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            listOf(
                "Option 1 (enabled)" to Pair(option1, { v: Boolean -> option1 = v }),
                "Option 2 (disabled)" to Pair(option2, { v: Boolean -> option2 = v }),
                "Option 3" to Pair(option3, { v: Boolean -> option3 = v }),
                "Option 4 (checked)" to Pair(option4, { v: Boolean -> option4 = v }),
            ).forEachIndexed { idx, (label, pair) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = pair.first,
                        onCheckedChange = pair.second,
                        enabled = idx != 1
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(label, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(Modifier.height(16.dp))
            val anyChecked = option1 || option3 || option4
            Text(
                "Summary: ${if (anyChecked) "Some options selected" else "Nothing selected"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
