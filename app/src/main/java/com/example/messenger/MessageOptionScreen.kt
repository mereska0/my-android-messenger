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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

@Composable
fun MessageOptionScreen(message: Message, onDelete: () -> Unit, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .clickable{onBack()}
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message.text,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = onDelete,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Delete")
        }
    }
}