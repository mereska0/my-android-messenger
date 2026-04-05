package com.example.messenger.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.messenger.viewmodel.Message

@Composable
fun ChatScreen(messages: List<Message>,
               onSend: (id: Int, text: String, isFromMe: Boolean) -> Unit,
               onBack: () -> Unit,
               onMessageClick: (Message) -> Unit) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val chatId = messages.lastOrNull()?.chatId ?: 0

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(0)
    }
    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBack) {
                Text("Back")
            }
            Text(
                text = "Chat $chatId",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.width(60.dp))
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),

            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clickable{ onMessageClick(message)},
                    horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start
                ) {
                    Text(
                        text = message.text,
                        modifier = Modifier
                            .background(
                                color = if (message.isFromMe) Color(0xFF1976D2) else Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                    bottomStart = if (message.isFromMe) 16.dp else 4.dp,
                                    bottomEnd = if (message.isFromMe) 4.dp else 16.dp
                                )
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        color = if (message.isFromMe) Color.White else Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message..") },
                shape = RoundedCornerShape(24.dp)
            )

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        onSend(chatId, inputText, true)
                        inputText = ""
                    }
                }
            ) {
                Text("Send")
            }
            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        onSend(chatId, inputText, false)
                        inputText = ""
                    }
                }
            ) {
                Text("Reply(Fake)")
            }
        }
    }
}