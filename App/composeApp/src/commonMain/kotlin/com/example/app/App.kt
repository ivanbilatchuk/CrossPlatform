package com.example.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.navigation.Screen
import com.example.app.screens.*
import com.example.app.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route)         { HomeScreen(navController) }
            composable(Screen.DateTime.route)     { DateTimeScreen(navController) }
            composable(Screen.Buttons.route)      { ButtonsScreen(navController) }
            composable(Screen.Checkboxes.route)   { CheckboxesScreen(navController) }
            composable(Screen.Chips.route)        { ChipsScreen(navController) }
            composable(Screen.DatePicker.route)   { DatePickerScreen(navController) }
            composable(Screen.Dialog.route)       { DialogScreen(navController) }
            composable(Screen.Divider.route)      { DividerScreen(navController) }
            composable(Screen.ProgressBar.route)  { ProgressBarScreen(navController) }
            composable(Screen.RadioButtons.route) { RadioButtonsScreen(navController) }
            composable(Screen.Switch.route)       { SwitchScreen(navController) }
            composable(Screen.TimePicker.route)   { TimePickerScreen(navController) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(navController: androidx.navigation.NavController) {
    val navItems = listOf(
        "Date & Time (Lab 2)" to Screen.DateTime,
        "Buttons"           to Screen.Buttons,
        "Checkboxes"        to Screen.Checkboxes,
        "Chips"             to Screen.Chips,
        "Datepicker dialog" to Screen.DatePicker,
        "Dialog"            to Screen.Dialog,
        "Divider"           to Screen.Divider,
        "Progress bar"      to Screen.ProgressBar,
        "Radio buttons"     to Screen.RadioButtons,
        "Switch"            to Screen.Switch,
        "Timepicker dialog" to Screen.TimePicker,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Components Demo") },
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Select a component to explore",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))

            navItems.forEach { (label, screen) ->
                Button(
                    onClick = { navController.navigate(screen.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(label)
                }
            }
        }
    }
}

