package com.example.messenger

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

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