package com.example.messenger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.example.messenger.viewmodel.Message

@Composable
fun MessageOptionScreen(
    message: Message,
    onDelete: () -> Unit,
    onEdit: (String) -> Unit,
    onBack: () -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(message.text) }

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {onBack()}
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (isEditing) {

            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .padding(16.dp)
                    .focusRequester(focusRequester)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Button(onClick = {
                    onEdit(text)
                    isEditing = false
                }) {
                    Text("Save")
                }

                Button(onClick = {
                    isEditing = false
                    text = message.text
                }) {
                    Text("Cancel")
                }
            }

        } else {

            Text(
                text = message.text,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Button(onClick = { isEditing = true }) {
                    Text("Edit")
                }

                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


    }
    LaunchedEffect(isEditing) {
        if (isEditing) {
            focusRequester.requestFocus()
        }
    }
}