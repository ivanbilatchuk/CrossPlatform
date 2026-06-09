package com.example.app.ui.reminders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.viewmodel.koinViewModel
import com.example.app.data.reminders.Reminder
import com.example.app.data.reminders.RemindersRepository

@Composable
internal fun RemindersPage(
    onAboutButtonClick: () -> Unit,
    viewModel: ReminderViewModel= koinViewModel()
) {
    Column {
        Toolbar(onAboutButtonClick = onAboutButtonClick)
        RemindersContent(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    onAboutButtonClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Reminders") },
        actions = {
            IconButton(onClick = onAboutButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "About Device Button",
                )
            }
        }
    )
}

@Composable
private fun RemindersContent(viewModel: ReminderViewModel) {

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var shouldFocusOnTextField by remember { mutableStateOf(false) }

    var reminders by remember {
        focusManager.clearFocus(true)
        mutableStateOf(listOf<Reminder>(), policy = neverEqualPolicy())
    }

    var textFieldValue by remember { mutableStateOf("") }

    viewModel.onRemindersUpdated = { newList ->
        reminders = newList
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = reminders) { item ->
            val onItemClick = {
                viewModel.markReminder(id = item.id, isCompleted = !item.isCompleted)
            }
            ReminderItem(
                title = item.title,
                isCompleted = item.isCompleted,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true, onClick = onItemClick)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        item {
            val onSubmit = {
                viewModel.createReminder(title = textFieldValue)
                textFieldValue = ""
            }

            NewReminderTextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                onSubmit = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )

            LaunchedEffect(reminders) {
                if (shouldFocusOnTextField) {
                    focusRequester.requestFocus()
                    shouldFocusOnTextField = false
                }
            }
        }
    }
}

@Composable
private fun ReminderItem(title: String, isCompleted: Boolean, modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Checkbox(
            checked = isCompleted,
            onCheckedChange = { }
        )

        Text(
            text = title,
            style = if (isCompleted) {
                MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.LineThrough,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                )
            } else {
                MaterialTheme.typography.bodyLarge
            },
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Composable
private fun NewReminderTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Add a new reminder here") },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = { onSubmit() }
        ),
        modifier = modifier
            .onPreviewKeyEvent { event: KeyEvent ->
                if (event.key == Key.Enter) {
                    onSubmit()
                    return@onPreviewKeyEvent true
                }
                false
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun RemindersViewPreview() {
    RemindersPage(
        onAboutButtonClick = {},
    )
}