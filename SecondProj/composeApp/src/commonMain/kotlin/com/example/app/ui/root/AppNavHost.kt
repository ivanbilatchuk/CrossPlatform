package com.example.app.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app.ui.about.AboutScreen
import com.example.app.ui.reminders.RemindersPage

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Reminders.route,
        modifier = modifier,
    ) {
        composable(Screen.Reminders.route) {
            RemindersPage(
                onAboutButtonClick = { navController.navigate(Screen.AboutDevice.route) }
            )
        }

        composable(Screen.AboutDevice.route) {
            AboutScreen(
                onUpButtonClick = { navController.popBackStack() }
            )
        }
    }
}