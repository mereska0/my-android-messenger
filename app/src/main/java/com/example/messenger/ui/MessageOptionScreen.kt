package com.example.messenger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.messenger.viewmodel.ChatViewModel

@Composable
fun MessageOptionScreen(
    messageId: Int,
    viewModel: ChatViewModel,
    onBack: () -> Unit
) {

    val message by viewModel.getMessageById(messageId)
        .collectAsStateWithLifecycle(initialValue = null)

    if (message == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(message!!.text) }

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .clickable{onBack()},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 🔹 MESSAGE BUBBLE
        Text(
            text = message!!.text,
            modifier = Modifier
                .background(
                    color = if (message!!.isFromMe)
                        Color(0xFF1976D2)
                    else
                        Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            color = if (message!!.isFromMe) Color.White else Color.Black,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(24.dp))

        // 🔹 EDIT MODE
        if (isEditing) {

            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Button(onClick = {
                    viewModel.editMessage(messageId, text)
                    isEditing = false
                }) {
                    Text("Save")
                }

                OutlinedButton(onClick = {
                    text = message!!.text
                    isEditing = false
                }) {
                    Text("Cancel")
                }
            }

        } else {

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                if (message!!.isFromMe) {
                    Button(onClick = { isEditing = true }) {
                        Text("Edit")
                    }
                }

                Button(
                    onClick = {
                        message?.let {
                            viewModel.deleteMessage(it)
                            onBack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete")
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }

    LaunchedEffect(isEditing) {
        if (isEditing) focusRequester.requestFocus()
    }
}