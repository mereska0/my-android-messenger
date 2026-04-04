package com.example.messenger

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ChatViewModel: ViewModel() {
    private val _chats = List(40){
        Message(it,
            (0..19).random(),
            (100..200).random().toString(),
            (0..1).random() != 0
        )
    }
    val chats = mutableStateListOf<Message>().apply {
        addAll(_chats)
    }
    fun sendMessage(chatId: Int, text: String, isFromMe: Boolean){
        val newId = (chats.lastOrNull()?.id ?: 0) + 1
        chats.add(Message(newId, chatId, text, isFromMe))
    }
    fun getContacts(): List<Chat> {
        return (0..19).map { chatId ->
            val lastMessage = chats
                .filter { it.chatId == chatId }
                .lastOrNull()?.text ?: "No messages"

            Chat(chatId, "chat$chatId", lastMessage)
        }
    }
}
data class Chat(
    val id: Int,
    val name: String,
    val lastMessage: String
)
data class Message(
    val id: Int,
    val chatId: Int,
    val text: String,
    val isFromMe: Boolean
)