package com.example.app.navigation

sealed class Screen(val route: String) {
    data object Home        : Screen("home")
    data object DateTime    : Screen("datetime")
    data object Buttons     : Screen("buttons")
    data object Checkboxes  : Screen("checkboxes")
    data object Chips       : Screen("chips")
    data object DatePicker  : Screen("datepicker")
    data object Dialog      : Screen("dialog")
    data object Divider     : Screen("divider")
    data object ProgressBar : Screen("progressbar")
    data object RadioButtons: Screen("radiobuttons")
    data object Switch      : Screen("switch")
    data object TimePicker  : Screen("timepicker")
}
